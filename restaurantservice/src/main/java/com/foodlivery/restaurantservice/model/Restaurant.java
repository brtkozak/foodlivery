package com.foodlivery.restaurantservice.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    private String id;
    private String name;
    private Address address;
    private List<Category> categories;
    private double minOrderPrice;

    public Restaurant(String name, Address address, List<Category> categories, double minOrderPrice) {
        this.name = name;
        this.address = address;
        this.categories = categories;
        this.minOrderPrice = minOrderPrice;
    }

    public enum Category {
        ITALIAN("Italian"),
        EUROPEAN("European"),
        SUSHI("Sushi");

        private String name;

        Category (String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

