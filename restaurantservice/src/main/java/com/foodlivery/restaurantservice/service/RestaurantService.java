package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.model.reponse.PageResponse;
import com.foodlivery.restaurantservice.model.reponse.RestaurantWithDishesResponse;
import com.foodlivery.restaurantservice.repository.RestaurantRepository;
import com.foodlivery.restaurantservice.router.RestaurantRouter;
import com.foodlivery.restaurantservice.utils.RestaurantConverter;
import com.google.common.primitives.Ints;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private DishService dishService;
    private RestaurantConverter restaurantConverter;

    public RestaurantService(RestaurantRepository restaurantRepository, DishService dishService, RestaurantConverter restaurantConverter) {
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
        this.restaurantConverter = restaurantConverter;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return restaurantRepository.findAll()
                .collectList()
                .flatMap(restaurants ->
                        ServerResponse.ok().body(Mono.just(restaurants), Restaurant.class));
    }

    public Mono<ServerResponse> getPage(ServerRequest request) {
        int pageNumber = request.queryParam("page").map(Ints::tryParse).orElse(PageResponse.FIRST_PAGE);
        int size = request.queryParam("size").map(Ints::tryParse).orElse(PageResponse.DEFAULT_PAGE_SIZE);
        Pageable page = PageRequest.of(pageNumber, size);

        Mono<List<Restaurant>> restaurants =
                restaurantRepository.findAll()
                        .skip(pageNumber * size)
                        .take(size)
                        .collectList();

        Mono<Integer> allElementsCount =
                restaurantRepository
                        .findAll()
                        .collectList()
                        .map(List::size);

        return Mono.zip(restaurants, allElementsCount)
                .map( it -> new PageResponse<Restaurant>(pageNumber, size, it.getT2(), it.getT1()))
                .flatMap(it -> ServerResponse.ok().body(Mono.just(it), PageResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurant(ServerRequest request) {
        String restaurantId = request.pathVariable(RestaurantRouter.PATH_VARIABLE_RESTAURANT_ID);
        Mono<List<Dish>> dishes = dishService.getDishesForRestaurant(restaurantId);
        Mono<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        return Mono.zip(restaurant, dishes)
                .map(it -> restaurantConverter.getRestaurantWithDishes(it.getT1(), it.getT2()))
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), RestaurantWithDishesResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }




}
