package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.SimpleRating;
import com.foodlivery.restaurantservice.model.dto.ComplexRatingDto;
import com.foodlivery.restaurantservice.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RatingService extends BaseService {

    public RatingService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<SimpleRating> getSimpleRatingForRestaurant(String restaurantId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_RATING + "/simple-rating-for-restaurant/" + restaurantId)
                .retrieve()
                .bodyToMono(SimpleRating.class)
                .onErrorReturn(new SimpleRating(0, 0));
    }

    public Mono<ComplexRatingDto> getComplexRatingForRestaurant(String restaurantId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_RATING + "/complex-rating-for-restaurant/" + restaurantId)
                .retrieve()
                .bodyToMono(ComplexRatingDto.class);
    }
}
