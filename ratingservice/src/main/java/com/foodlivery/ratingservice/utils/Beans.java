package com.foodlivery.ratingservice.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Beans {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }

}
