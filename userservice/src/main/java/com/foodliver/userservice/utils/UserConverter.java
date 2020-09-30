package com.foodliver.userservice.utils;

import com.foodliver.userservice.model.User;
import com.foodliver.userservice.model.request.UserRequest;
import com.foodliver.userservice.model.response.UserResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserConverter {

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public  User userRequestToUser(UserRequest userRequest) {
        return new User(
                userRequest.getEmail(),
                encodePassword(userRequest.getPassword()),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getAddress(),
                userRequest.getPhoneNo());
    }

    public  UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhoneNo());
    }

    private String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }
}
