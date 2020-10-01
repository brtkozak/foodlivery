package com.foodlivery.dishservice.service;

import com.foodlivery.dishservice.model.Dish;
import com.foodlivery.dishservice.repository.DishRepository;
import com.foodlivery.dishservice.router.DishRouter;
import com.foodlivery.dishservice.utils.Constants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class DishService {

    private DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Mono<ServerResponse> getDishesForRestaurant(ServerRequest request) {
        return dishRepository.findAllByRestaurantId(request.pathVariable(Constants.PATH_VARIABLE_RESTAURANT_ID))
                .collectList()
                .flatMap(dishes ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(dishes), Dish.class));
    }

    public Mono<ServerResponse> getDish(ServerRequest request) {
        return dishRepository.findById(request.pathVariable(Constants.PATH_VARIABLE_RESTAURANT_ID))
                .flatMap(dish -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(dish), Dish.class));
    }

}
