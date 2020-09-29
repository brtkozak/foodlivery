package com.foodlivery.restaurantservice.handler;

import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class RestaurantHandler {

    private RestaurantRepository restaurantRepository;

    public RestaurantHandler(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Mono<ServerResponse> getAll (ServerRequest request) {
        return restaurantRepository.findAll()
                .collectList()
                .flatMap(restaurants ->
                        ServerResponse.ok().body(Mono.just(restaurants), Restaurant.class));
    }

}
