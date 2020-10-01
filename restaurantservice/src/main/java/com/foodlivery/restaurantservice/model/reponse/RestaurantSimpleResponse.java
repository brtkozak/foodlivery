package com.foodlivery.restaurantservice.model.reponse;

import com.foodlivery.restaurantservice.model.Address;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.model.SimpleRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantSimpleResponse {

    private String id;
    private String name;
    private Address address;
    private List<Restaurant.Category> categories;
    private double minOrderPrice;
    private SimpleRating rating;

}
