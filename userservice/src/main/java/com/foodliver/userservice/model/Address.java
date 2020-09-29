package com.foodliver.userservice.model;

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
