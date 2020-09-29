package com.foodlivery.restaurantservice.repository;

import com.foodlivery.restaurantservice.model.Restaurant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RestaurantRepository extends ReactiveMongoRepository<Restaurant, String> {

}
