package com.foodlivery.orderservice.service;

import com.foodlivery.orderservice.model.Dish;
import com.foodlivery.orderservice.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DishService extends BaseService {

    public DishService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    Mono<Double> getTotalPriceForDishes(List<String> dishesIds) {
        return Flux.fromIterable(dishesIds)
                .flatMap(this::getDish)
                .map(Dish::getPrice)
                .collectList()
                .map(it -> it.stream().mapToDouble(Double::doubleValue).sum());
    }

    public Mono<Dish> getDish(String dishId) {
        return webClient
                .get()
                .uri("http://"+ Constants.SERVICE_PATH_DISH + "/" + dishId)
                .retrieve()
                .bodyToMono(Dish.class);
    }

}
