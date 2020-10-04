package com.foodliver.userservice.service;

import com.foodliver.userservice.model.dto.OrderDto;
import com.foodliver.userservice.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderService extends BaseService{

    public OrderService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<OrderDto> getOrdersForUser(String userId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_ORDER + "/order-for-customer/" + userId)
                .retrieve()
                .bodyToMono(OrderDto.class);
    }
}
