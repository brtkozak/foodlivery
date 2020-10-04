package com.foodliver.userservice.model.response;

import com.foodliver.userservice.model.Address;
import com.foodliver.userservice.model.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    String id;
    String customerId;
    String restaurantId;
    Address address;
    String phoneNo;
    List<Dish> dishes;
    double totalPrice;
    LocalDate createdDate;
}
