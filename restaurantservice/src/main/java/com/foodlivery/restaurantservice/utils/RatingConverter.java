package com.foodlivery.restaurantservice.utils;

import com.foodlivery.restaurantservice.model.ComplexRating;
import com.foodlivery.restaurantservice.model.Rating;
import com.foodlivery.restaurantservice.model.User;
import com.foodlivery.restaurantservice.model.dto.ComplexRatingDto;
import com.foodlivery.restaurantservice.model.dto.RatingDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingConverter {

    public static Rating getRating(RatingDto ratingDto, User user) {
        return new Rating(user, ratingDto.getRate(), ratingDto.getDescription());
    }

    public static ComplexRating getComplexRating(List<Rating> ratings, ComplexRatingDto complexRatingDto){
        return new ComplexRating(complexRatingDto.getAverage(), complexRatingDto.getRatingsCount(), ratings);
    }

}
