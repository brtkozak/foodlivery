package com.foodlivery.restaurantservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplexRatingDto {
    private double average;
    private int ratingsCount;
    private List<RatingDto> ratings;
}

