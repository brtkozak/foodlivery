package com.foodlivery.ratingservice.service;

import com.foodlivery.ratingservice.router.ClientException;
import com.foodlivery.ratingservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestaurantService extends BaseService {

    public RestaurantService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<String> getRestaurant(String restaurantId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_RESTAURANT + "/" + restaurantId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new ClientException("Restaurant with id " + restaurantId + " not found.", response)))
                .bodyToMono(String.class);
    }

}
