package com.foodlivery.orderservice.service;

import com.foodlivery.orderservice.model.Order;
import com.foodlivery.orderservice.repository.OrderRepository;
import com.foodlivery.orderservice.utils.Constants;
import com.foodlivery.orderservice.utils.RequestValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class OrderService {

    OrderRepository orderRepository;
    DishService dishService;
    RequestValidator requestValidator;

    public OrderService(OrderRepository orderRepository, DishService dishService, RequestValidator requestValidator) {
        this.orderRepository = orderRepository;
        this.dishService = dishService;
        this.requestValidator = requestValidator;
    }

    public Mono<ServerResponse> addOrder(ServerRequest request) {
        Mono<Order> orderRequestBody = request.bodyToMono(Order.class)
                .flatMap(requestValidator::validate);

        return orderRequestBody.flatMap(order ->
                dishService.getTotalPriceForDishes(order.getDishesIds())
                        .map(it -> {
                            order.setTotalPrice(it);
                            return order;
                        })
                        .flatMap(it -> orderRepository.insert(it))
                        .flatMap(it -> ServerResponse.created(URI.create(request.uri() + "/" + it.getId())).contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Order.class)));

    }

    public Mono<ServerResponse> getOrder(ServerRequest request) {
        String orderId = request.pathVariable(Constants.PATH_VARIABLE_ORDER_ID);
        return orderRepository
                .findById(orderId)
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Order.class));
    }

    public Mono<ServerResponse> getCustomerOrders(ServerRequest request) {
        String customerId = request.pathVariable(Constants.PATH_VARIABLE_USER_ID);
        return orderRepository
                .findAllByCustomerId(customerId)
                .collectList()
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Order.class));
    }

}
