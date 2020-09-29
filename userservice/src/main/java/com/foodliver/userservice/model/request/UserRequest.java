package com.foodliver.userservice.model.request;

import com.foodliver.userservice.model.Address;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotEmpty(message = "Email can not be empty")
    private String email;
    @NotEmpty(message = "Password can not be empty")
    private String password;
    @NotEmpty(message = "First name can not be empty")
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    private String lastName;
    private Address address;
    @Size(min = 9, message = "Invalid phone number length")
    private String phoneNo;

}
