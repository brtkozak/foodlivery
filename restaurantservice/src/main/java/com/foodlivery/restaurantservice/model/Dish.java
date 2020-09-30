package com.foodlivery.restaurantservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

    private String id;
    private String restaurantId;
    private String name;
    private double price;
    private List<String> ingredients;

}
