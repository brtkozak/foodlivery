package com.foodliver.userservice.model.dto;

import com.foodliver.userservice.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    String id;
    String customerId;
    String restaurantId;
    Address address;
    String phoneNo;
    List<String> dishesIds;
    double totalPrice;
    LocalDate createdDate;
}
