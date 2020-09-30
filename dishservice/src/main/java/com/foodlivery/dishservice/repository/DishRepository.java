package com.foodlivery.dishservice.repository;

import com.foodlivery.dishservice.model.Dish;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DishRepository extends ReactiveMongoRepository<Dish, String> {

    Flux<Dish> findAllByRestaurantId(String restaurantId);

}
