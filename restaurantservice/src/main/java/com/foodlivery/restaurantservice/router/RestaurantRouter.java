package com.foodlivery.restaurantservice.router;

import com.foodlivery.restaurantservice.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.foodlivery.restaurantservice.utils.Constants.BASE_ENDPOINT;
import static com.foodlivery.restaurantservice.utils.Constants.PATH_VARIABLE_RESTAURANT_ID;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RestaurantRouter {

    private RestaurantService restaurantService;

    public RestaurantRouter(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Bean
    RouterFunction<ServerResponse> restaurantRouting() {
        return RouterFunctions
                .route(GET(BASE_ENDPOINT + ""), restaurantService::getRestaurants)
                .andRoute(GET(BASE_ENDPOINT + "/{" + PATH_VARIABLE_RESTAURANT_ID + "}"), restaurantService::getRestaurant);
    }

}
