package com.foodliver.userservice.service;

import org.springframework.web.reactive.function.client.WebClient;

abstract public class BaseService {

    WebClient webClient;

    public BaseService(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder().build();
    }

}
