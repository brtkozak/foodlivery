package com.foodlivery.ratingservice.service;

import com.foodlivery.ratingservice.model.Rating;
import com.foodlivery.ratingservice.model.response.SimpleRatingResponse;
import com.foodlivery.ratingservice.repository.RatingRepository;
import com.foodlivery.ratingservice.utils.Constants;
import com.foodlivery.ratingservice.utils.RatingConverter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RatingService {

    RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Mono<ServerResponse> getSimpleRatingForRestaurant(ServerRequest request) {
        String restaurantId = request.pathVariable(Constants.PATH_VARIABLE_RESTAURANT_ID);
        Flux<Rating> ratings = ratingRepository.findAllByRestaurantId(restaurantId);
        return ratings
                .collectList()
                .map(RatingConverter::getSimpleRating)
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), SimpleRatingResponse.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addRating(ServerRequest request) {
        Mono<Rating> rating = request.bodyToMono(Rating.class);
        // TODO check if user and restaurant exist here
        return rating.flatMap(
                ratingRepository::insert)
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Rating.class));
    }
}
