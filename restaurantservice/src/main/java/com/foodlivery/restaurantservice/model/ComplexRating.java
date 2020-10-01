package com.foodlivery.restaurantservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexRating {
    private double average;
    private int ratingsCount;
    List<Rating> ratings;
}

