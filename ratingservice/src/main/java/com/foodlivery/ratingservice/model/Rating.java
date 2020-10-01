package com.foodlivery.ratingservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rating")
public class Rating {

    @Id
    private String id;
    private String restaurantId;
    private String userId;
    private int rate;
    private String description;

}
