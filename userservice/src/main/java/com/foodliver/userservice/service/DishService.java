package com.foodliver.userservice.service;

import com.foodliver.userservice.model.Dish;
import com.foodliver.userservice.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DishService extends BaseService {

    public DishService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<Dish> getDish(String dishId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_DISH + "/" + dishId)
                .retrieve()
                .bodyToMono(Dish.class);
    }

}
