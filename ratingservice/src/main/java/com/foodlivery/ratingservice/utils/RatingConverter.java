package com.foodlivery.ratingservice.utils;

import com.foodlivery.ratingservice.model.Rating;
import com.foodlivery.ratingservice.model.response.SimpleRatingResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingConverter {

    public static SimpleRatingResponse getSimpleRating(List<Rating> ratings) {
        double avg = ratings.stream().map(Rating::getRate).mapToInt(Integer::intValue).average().orElse(0d);
        return SimpleRating(avg, ratings.size());
    }

}
