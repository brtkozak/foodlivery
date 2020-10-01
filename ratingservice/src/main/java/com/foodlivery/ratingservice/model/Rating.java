package com.foodlivery.ratingservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rating")
public class Rating {

    @Id
    private String id;
    private String restaurantId;
    private String userId;
    @Range(min = 0, max = 5, message = "The rate should be between 1 and 5")
    private int rate;
    private String description;

}
