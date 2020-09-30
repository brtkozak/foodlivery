package com.foodlivery.restaurantservice.service;


import com.foodlivery.restaurantservice.model.Dish;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DishService {

    private WebClient webClient;

    public DishService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<List<Dish>> getDishesForRestaurant(String restaurantId) {
        return webClient
                .get()
                .uri("http://dishservice/dish/dish-for-restaurant/" + restaurantId)
                .retrieve()
                .bodyToFlux(Dish.class)
                .collectList();
    }

}
