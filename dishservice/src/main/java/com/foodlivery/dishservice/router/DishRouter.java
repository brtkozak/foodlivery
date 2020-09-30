package com.foodlivery.dishservice.router;


import com.foodlivery.dishservice.service.DishService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class DishRouter  {

    public static String BASE_ENDPOINT = "/dish";
    public static String PATH_VARIABLE_RESTAURANT_ID = "restaurantId";

    private DishService dishService;

    public DishRouter(DishService dishService) {
        this.dishService = dishService;
    }

    @Bean
    RouterFunction<ServerResponse> dishRouting() {
        return RouterFunctions
                .route(GET(BASE_ENDPOINT + "/dish-for-restaurant/{" + PATH_VARIABLE_RESTAURANT_ID + "}"), dishService::getDishesForRestaurant);
    }

}