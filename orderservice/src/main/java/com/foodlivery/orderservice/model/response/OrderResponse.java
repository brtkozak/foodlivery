package com.foodlivery.orderservice.model.response;

import com.foodlivery.orderservice.model.Address;
import com.foodlivery.orderservice.model.Dish;
import java.time.LocalDate;
import java.util.List;

public class OrderResponse {
    String id;
    String customerId;
    String restaurantId;
    Address address;
    String phoneNo;
    double totalPrice;
    List<Dish> dishesIds;
    LocalDate createdDate;
}
