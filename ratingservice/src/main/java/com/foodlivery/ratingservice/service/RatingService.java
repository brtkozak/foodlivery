package com.foodlivery.ratingservice.service;

import com.foodlivery.ratingservice.model.Rating;
import com.foodlivery.ratingservice.model.response.SimpleRatingResponse;
import com.foodlivery.ratingservice.repository.RatingRepository;
import com.foodlivery.ratingservice.router.ClientException;
import com.foodlivery.ratingservice.utils.Constants;
import com.foodlivery.ratingservice.utils.RatingConverter;
import com.foodlivery.ratingservice.utils.RequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class RatingService extends BaseService{

    RatingRepository ratingRepository;
    RequestValidator requestValidator;
    RestaurantService restaurantService;
    UserService userService;

    public RatingService(RatingRepository ratingRepository, WebClient.Builder webClientBuilder, RequestValidator requestValidator, RestaurantService restaurantService, UserService userService) {
        super(webClientBuilder);
        this.ratingRepository = ratingRepository;
        this.requestValidator = requestValidator;
        this.restaurantService = restaurantService;
        this.userService = userService;
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
        Mono<Rating> rating = request.bodyToMono(Rating.class)
                .flatMap(requestValidator::validate);

        return rating.flatMap(ratingRequestBody -> {
                    Mono<String> user = userService.getUser(ratingRequestBody.getUserId());
                    Mono<String> restaurant = restaurantService.getRestaurant(ratingRequestBody.getRestaurantId());
                    Mono<Object> ratingExist =
                            ratingRepository
                                    .findByRestaurantIdAndUserId(ratingRequestBody.getRestaurantId(), ratingRequestBody.getUserId())
                                    .flatMap(it -> Mono.error(new ClientException("User has already added a rating of this restaurant.", null)))
                                    .switchIfEmpty(Mono.just(true));

                    return Mono.zip(user, restaurant, ratingExist)
                            .flatMap(it -> ratingRepository.insert(ratingRequestBody))
                            .flatMap(it -> ServerResponse.created(URI.create(request.uri().toString() + "/" + it.getId())).contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Rating.class));
                }
        );
    }

    public Mono<ServerResponse> updateRating(ServerRequest request) {
        Mono<Rating> rating = request.bodyToMono(Rating.class)
                .flatMap(requestValidator::validate)
                .map(it -> {
                    it.setId(request.pathVariable(Constants.PATH_VARIABLE_RATING_ID));
                    return it;
                });

        return rating.flatMap(ratingRequestBody -> {
                    Mono<String> user = userService.getUser(ratingRequestBody.getUserId());
                    Mono<String> restaurant = restaurantService.getRestaurant(ratingRequestBody.getRestaurantId());

                    return Mono.zip(user, restaurant)
                            .flatMap(it -> ratingRepository.save(ratingRequestBody))
                            .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Rating.class));
                }
        );
    }
}
