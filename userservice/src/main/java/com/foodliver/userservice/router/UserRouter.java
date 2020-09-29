package com.foodliver.userservice.router;


import com.foodliver.userservice.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {

    public static final String BASE_ENDPOINT = "/user";

    private UserHandler userHandler;

    public UserRouter(UserHandler userHandler){
        this.userHandler = userHandler;
    }

    @Bean
    RouterFunction<ServerResponse> usersRouting() {
        return RouterFunctions.
                route(GET(BASE_ENDPOINT + "/{userId}"), userHandler::getUser)
                .andRoute(POST( BASE_ENDPOINT +"/register"), userHandler::registerUser)
                .andRoute(GET(BASE_ENDPOINT + "/get/all"), userHandler::getAll)
                .andRoute(PUT(BASE_ENDPOINT + "/{userId}"), userHandler::updateUser);
    }

}
