package com.foodlivery.restaurantservice.utils;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Beans {

    MongoClient mongoClient;

    public Beans(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient, "restaurant_database");
    }

}

