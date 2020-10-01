package com.foodlivery.ratingservice.service;

import org.springframework.web.reactive.function.client.WebClient;

abstract public class BaseService {

    WebClient webClient;

    public BaseService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

}
