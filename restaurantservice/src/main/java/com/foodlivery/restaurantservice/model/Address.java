package com.foodlivery.restaurantservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    String city;
    String street;
    int buildingNo;
    int homeNo;
    String zipCode;
}
