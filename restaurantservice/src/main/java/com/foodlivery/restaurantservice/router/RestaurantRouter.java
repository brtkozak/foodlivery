package com.foodlivery.restaurantservice.router;

import com.foodlivery.restaurantservice.handler.RestaurantHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RestaurantRouter {

    public static String BASE_ENDPOINT = "/restaurant";

    private RestaurantHandler restaurantHandler;


    public RestaurantRouter(RestaurantHandler restaurantHandler) {
        this.restaurantHandler = restaurantHandler;
    }

    @Bean
    RouterFunction<ServerResponse> restaurantRouting() {
        return RouterFunctions
                .route(GET(BASE_ENDPOINT + "/get/all"), restaurantHandler::getAll);
    }

}
