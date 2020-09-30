package com.foodlivery.restaurantservice.utils;

import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.model.reponse.RestaurantWithDishesResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantConverter {

    public RestaurantWithDishesResponse getRestaurantWithDishes(Restaurant restaurant, List<Dish> dishes) {
        return new RestaurantWithDishesResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCategories(),
                restaurant.getMinOrderPrice(),
                dishes
                );
    }

}
