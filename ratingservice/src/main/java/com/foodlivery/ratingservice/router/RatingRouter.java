package com.foodlivery.ratingservice.router;

import com.foodlivery.ratingservice.service.RatingService;
import com.foodlivery.ratingservice.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@Configuration
public class RatingRouter {

    private RatingService ratingService;

    public RatingRouter(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Bean
    RouterFunction<ServerResponse> ratingRouting() {
        return RouterFunctions
                .route(GET(Constants.BASE_ENDPOINT + "/simple-rating-for-restaurant/{" + Constants.PATH_VARIABLE_RESTAURANT_ID + "}"), ratingService::getSimpleRatingForRestaurant)
                .andRoute(GET(Constants.BASE_ENDPOINT + "/complex-rating-for-restaurant/{" + Constants.PATH_VARIABLE_RESTAURANT_ID + "}"), ratingService::getComplexRatingForRestaurant)
                .andRoute(POST(Constants.BASE_ENDPOINT), ratingService::addRating)
                .andRoute(PUT(Constants.BASE_ENDPOINT + "/{" + Constants.PATH_VARIABLE_RATING_ID + "}"), ratingService::updateRating);
    }

}
