package com.foodlivery.ratingservice.model.response;

import com.foodlivery.ratingservice.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplexRatingResponse {
    private double average;
    private int ratingsCount;
    private List<Rating> ratings;
}
