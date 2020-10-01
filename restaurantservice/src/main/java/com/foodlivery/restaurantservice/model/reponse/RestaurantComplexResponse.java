package com.foodlivery.restaurantservice.model.reponse;

import com.foodlivery.restaurantservice.model.Address;
import com.foodlivery.restaurantservice.model.Dish;
import com.foodlivery.restaurantservice.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantComplexResponse {

    public String id;
    public String name;
    public Address address;
    public List<Restaurant.Category> categories;
    double minOrderPrice;
    public List<Dish> dishes;

}
