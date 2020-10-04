package com.foodlivery.orderservice.router;

import com.foodlivery.orderservice.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.foodlivery.orderservice.utils.Constants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class OrderRouter {

    private OrderService orderService;

    public OrderRouter(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    RouterFunction<ServerResponse> orderRouting() {
        return RouterFunctions
                .route(POST(BASE_ENDPOINT), orderService::addOrder)
                .andRoute(GET(BASE_ENDPOINT + "/{" + PATH_VARIABLE_ORDER_ID + "}"), orderService::getOrder)
                .andRoute(GET(BASE_ENDPOINT + "/order-for-customer/{" + PATH_VARIABLE_USER_ID + "}"), orderService::getCustomerOrders);
    }

}
