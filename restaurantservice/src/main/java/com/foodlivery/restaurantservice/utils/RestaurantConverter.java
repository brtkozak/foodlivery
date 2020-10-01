package com.foodlivery.restaurantservice.utils;

import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.model.SimpleRating;
import com.foodlivery.restaurantservice.model.reponse.RestaurantComplexResponse;
import com.foodlivery.restaurantservice.model.reponse.RestaurantSimpleResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantConverter {

    public RestaurantComplexResponse getRestaurantWithDishes(Restaurant restaurant, List<Dish> dishes) {
        return new RestaurantComplexResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCategories(),
                restaurant.getMinOrderPrice(),
                dishes
                );
    }

    public RestaurantSimpleResponse getRestaurantSimpleResponse(Restaurant restaurant, SimpleRating simpleRating) {
        return new RestaurantSimpleResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCategories(),
                restaurant.getMinOrderPrice(),
                simpleRating
        );
    }

}
