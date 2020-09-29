package com.foodliver.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private int buildingNo;
    private int homeNo;
    private String phoneNo;

    public static UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getStreet(),
                user.getCity(),
                user.getBuildingNo(),
                user.getHomeNo(),
                user.getPhoneNo());
    }

}
