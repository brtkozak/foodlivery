package com.foodlivery.orderservice.service;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class BaseService {

    WebClient webClient;

    public BaseService(WebClient.Builder webClientBuilder){
        this.webClient =webClientBuilder.build();
    }

}
