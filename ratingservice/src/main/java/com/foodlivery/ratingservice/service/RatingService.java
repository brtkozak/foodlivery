package com.foodlivery.ratingservice.service;

import com.foodlivery.ratingservice.model.Rating;
import com.foodlivery.ratingservice.model.response.SimpleRatingResponse;
import com.foodlivery.ratingservice.repository.RatingRepository;
import com.foodlivery.ratingservice.utils.Constants;
import com.foodlivery.ratingservice.utils.RatingConverter;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class RatingService {

    RatingRepository ratingRepository;
    WebClient webClient;

    public RatingService(RatingRepository ratingRepository, WebClient.Builder webClientBuilder) {
        this.ratingRepository = ratingRepository;
        this.webClient = webClientBuilder.build();
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

//    public Mono<ServerResponse> addRating(ServerRequest request) {
//        Mono<Rating> rating = request.bodyToMono(Rating.class);
//        Mono<String> user = rating.flatMap(it ->
//                webClient
//                        .get()
//                        .uri("http://" + Constants.SERVICE_NAME_USER + "/" + it.getUserId())
//                        .retrieve()
//                        .bodyToMono(String.class)
//        );
//        Mono<String> restaurant = rating.flatMap(it ->
//                webClient
//                        .get()
//                        .uri("http://" + Constants.SERVICE_NAME_RESTAURANT + "/" + it.getRestaurantId())
//                        .retrieve()
//                        .bodyToMono(String.class)
//                );
//
//        return Mono.zip(user,rating)
//                .map(it ->
//                        it
//                ).then(rating)
//                .flatMap(ratingRepository::insert)
//                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Rating.class));
//    }


    public Mono<ServerResponse> addRating(ServerRequest request) {
        Mono<Rating> rating = request.bodyToMono(Rating.class);


        return rating.flatMap(ratingRequestBody -> {
                    Mono<String> user =
                            webClient
                                    .get()
                                    .uri("http://" + Constants.SERVICE_PATH_USER + "/" + ratingRequestBody.getUserId())
                                    .retrieve()
                                    .bodyToMono(String.class);
                    Mono<String> restaurant =
                            webClient
                                    .get()
                                    .uri("http://" + Constants.SERVICE_PATH_RESTAURANT + "/" + ratingRequestBody.getRestaurantId())
                                    .retrieve()
                                    .bodyToMono(String.class);
                    // TODO here implement on error return and handle exception inside global handler

                    return Mono.zip(user, restaurant)
                            .map(it ->
                                    it)
                            .flatMap(it -> ratingRepository.insert(ratingRequestBody))
                            .flatMap(it ->
                                    ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Rating.class));
                }
        );
    }
}
