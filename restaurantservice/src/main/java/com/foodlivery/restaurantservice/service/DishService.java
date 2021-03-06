package com.foodlivery.restaurantservice.service;


import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DishService extends BaseService{

    public DishService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<List<Dish>> getDishesForRestaurant(String restaurantId) {
        return webClient
                .get()
                .uri("http://"+ Constants.SERVICE_PATH_DISH +"/dish-for-restaurant/" + restaurantId)
                .retrieve()
                .bodyToFlux(Dish.class)
                .collectList();
    }

}
