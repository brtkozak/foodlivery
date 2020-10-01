package com.foodlivery.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private  String id;
    private String restaurantId;
    private String name;
    private double price;
    private List<String> ingredients;
}
