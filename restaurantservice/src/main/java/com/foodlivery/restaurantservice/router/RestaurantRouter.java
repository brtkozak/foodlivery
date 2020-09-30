package com.foodlivery.restaurantservice.router;

import com.foodlivery.restaurantservice.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RestaurantRouter {

    public static String BASE_ENDPOINT = "/restaurant";
    public static String PATH_VARIABLE_RESTAURANT_ID = "restaurantId";


    private RestaurantService restaurantService;

    public RestaurantRouter(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Bean
    RouterFunction<ServerResponse> restaurantRouting() {
        return RouterFunctions
                .route(GET(BASE_ENDPOINT + "/get/all"), restaurantService::getAll)
                .andRoute(GET(BASE_ENDPOINT + ""), restaurantService::getPage)
                .andRoute(GET(BASE_ENDPOINT + "/{" + PATH_VARIABLE_RESTAURANT_ID + "}"), restaurantService::getRestaurant);
    }

}
