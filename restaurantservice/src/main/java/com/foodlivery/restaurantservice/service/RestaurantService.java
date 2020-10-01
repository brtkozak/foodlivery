package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.model.reponse.PageResponse;
import com.foodlivery.restaurantservice.model.reponse.RestaurantWithDishesResponse;
import com.foodlivery.restaurantservice.repository.RestaurantRepository;
import com.foodlivery.restaurantservice.utils.Constants;
import com.foodlivery.restaurantservice.utils.RequestConverter;
import com.foodlivery.restaurantservice.utils.RestaurantConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private DishService dishService;
    private RestaurantConverter restaurantConverter;
    private ReactiveMongoTemplate reactiveMongoTemplate;


    public RestaurantService(RestaurantRepository restaurantRepository, DishService dishService, RestaurantConverter restaurantConverter, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
        this.restaurantConverter = restaurantConverter;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<ServerResponse> getRestaurants(ServerRequest request) {
        // pagination
        Pageable page = RequestConverter.getPageable(request);

        // filters
        String name = request.queryParam(Constants.QUERY_NAME).orElse(null);
        List<String> categories = RequestConverter.getValuesFromQueryParameter(request, Constants.QUERY_CATEGORY);
        List<String> cities = RequestConverter.getValuesFromQueryParameter(request, Constants.QUERY_CITY);

        // query building
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (name != null && !name.isEmpty()) {
            criteria = Criteria.where("name").regex(".*" + name + ".*");
            query.addCriteria(criteria);
        }
        if (categories != null) {
            criteria = Criteria.where("categories").in(categories);
            query.addCriteria(criteria);
        }
        if (cities != null) {
            criteria = Criteria.where("address.city").in(cities);
            query.addCriteria(criteria);
        }

        Flux<Restaurant> filteredRestaurants = reactiveMongoTemplate.find(query, Restaurant.class);

        Mono<List<Restaurant>> restaurants =
                filteredRestaurants
                        .skip(page.getPageNumber() * page.getPageSize())
                        .take(page.getPageSize())
                        .collectList();

        Mono<Long> allElementsCount =
                filteredRestaurants
                        .count();

        return Mono.zip(restaurants, allElementsCount)
                .map( it -> new PageResponse<Restaurant>(page.getPageNumber(), page.getPageSize(), it.getT2().intValue(), it.getT1()))
                .flatMap(it -> ServerResponse.ok().body(Mono.just(it), PageResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurant(ServerRequest request) {
        String restaurantId = request.pathVariable(Constants.PATH_VARIABLE_RESTAURANT_ID);
        Mono<List<Dish>> dishes = dishService.getDishesForRestaurant(restaurantId);
        Mono<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        return Mono.zip(restaurant, dishes)
                .map(it -> restaurantConverter.getRestaurantWithDishes(it.getT1(), it.getT2()))
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), RestaurantWithDishesResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
