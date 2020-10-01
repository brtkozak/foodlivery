package com.foodlivery.orderservice.repository;

import com.foodlivery.orderservice.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
    Flux<Order> findAllByCustomerId(String customerId);
}
