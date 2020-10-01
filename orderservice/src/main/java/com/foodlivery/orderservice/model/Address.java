package com.foodlivery.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    String city;
    String street;
    int buildingNo;
    int homeNo;
    String zipCode;
}
