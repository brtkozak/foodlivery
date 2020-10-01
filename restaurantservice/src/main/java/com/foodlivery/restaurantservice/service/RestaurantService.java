package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.ComplexRating;
import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.model.dto.ComplexRatingDto;
import com.foodlivery.restaurantservice.model.reponse.PageResponse;
import com.foodlivery.restaurantservice.model.reponse.RestaurantComplexResponse;
import com.foodlivery.restaurantservice.model.reponse.RestaurantSimpleResponse;
import com.foodlivery.restaurantservice.repository.RestaurantRepository;
import com.foodlivery.restaurantservice.utils.Constants;
import com.foodlivery.restaurantservice.utils.RatingConverter;
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
    private RatingService ratingService;
    private ReactiveMongoTemplate reactiveMongoTemplate;
    private UserService userService;

    public RestaurantService(RestaurantRepository restaurantRepository, DishService dishService, RestaurantConverter restaurantConverter, ReactiveMongoTemplate reactiveMongoTemplate, RatingService ratingService, UserService userService, RatingConverter ratingConverter) {
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.ratingService = ratingService;
        this.userService = userService;
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

        Mono<List<RestaurantSimpleResponse>> restaurants =
                filteredRestaurants
                        .skip(page.getPageNumber() * page.getPageSize())
                        .take(page.getPageSize())
                        .flatMap(restaurant -> ratingService.getSimpleRatingForRestaurant(restaurant.getId())
                                .map(rating -> RestaurantConverter.getRestaurantSimpleResponse(restaurant, rating)))
                        .collectList();

        Mono<Long> allElementsCount =
                filteredRestaurants
                        .count();

        return Mono.zip(restaurants, allElementsCount)
                .map( it -> new PageResponse<RestaurantSimpleResponse>(page.getPageNumber(), page.getPageSize(), it.getT2().intValue(), it.getT1()))
                .flatMap(it -> ServerResponse.ok().body(Mono.just(it), PageResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurant(ServerRequest request) {
        String restaurantId = request.pathVariable(Constants.PATH_VARIABLE_RESTAURANT_ID);
        Mono<List<Dish>> dishes = dishService.getDishesForRestaurant(restaurantId);
        Mono<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        Mono<ComplexRating> rating = getComplexRating(restaurantId);

        return Mono.zip(restaurant, dishes, rating)
                .map(it -> RestaurantConverter.getRestaurantComplexResponse(it.getT1(), it.getT2(), it.getT3()))
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), RestaurantComplexResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ComplexRating> getComplexRating(String restaurantId) {
        return ratingService.getComplexRatingForRestaurant(restaurantId).flatMap(
                complexRatingDto ->
                        Mono.just(complexRatingDto).map(ComplexRatingDto::getRatings)
                                .flatMapIterable(it -> it)
                                .flatMap(it -> userService.getUser(it.getUserId()).map(user -> RatingConverter.getRating(it, user)))
                                .collectList()
                                .map(it -> RatingConverter.getComplexRating(it, complexRatingDto))
        );
    }

}
