package com.foodlivery.ratingservice.repository;

import com.foodlivery.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {

    Flux<Rating> findAllByRestaurantId(String restaurantId);

}
