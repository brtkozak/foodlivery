package com.foodliver.userservice.model.response;

import com.foodliver.userservice.model.Address;
import com.foodliver.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;
    private String phoneNo;

}
