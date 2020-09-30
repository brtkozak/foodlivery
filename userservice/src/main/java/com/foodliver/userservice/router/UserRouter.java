package com.foodliver.userservice.router;


import com.foodliver.userservice.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {

    public static final String BASE_ENDPOINT = "/user";
    public static String PATH_VARIABLE_USER_ID = "userId";

    private UserService userService;

    public UserRouter(UserService userService){
        this.userService = userService;
    }

    @Bean
    RouterFunction<ServerResponse> usersRouting() {
        return RouterFunctions.
                route(GET(BASE_ENDPOINT + "/{" + PATH_VARIABLE_USER_ID + "}"), userService::getUser)
                .andRoute(POST( BASE_ENDPOINT +"/register"), userService::registerUser)
                .andRoute(GET(BASE_ENDPOINT + "/get/all"), userService::getAll)
                .andRoute(PUT(BASE_ENDPOINT + "/{" + PATH_VARIABLE_USER_ID + "}"), userService::updateUser);
    }

}
