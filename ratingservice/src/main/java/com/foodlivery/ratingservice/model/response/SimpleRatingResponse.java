package com.foodlivery.ratingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRatingResponse {

    private double average;
    private int ratingsCount;

}
