package com.foodlivery.orderservice.service;

import com.foodlivery.orderservice.model.Address;
import com.foodlivery.orderservice.model.Order;
import com.foodlivery.orderservice.repository.OrderRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class InitService {

    OrderRepository orderRepository;

    public InitService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mockRatings(){
        orderRepository
                .deleteAll()
                .thenMany(
                        Flux.just(
                                new Order("1", "1", "1", new Address("city", "street", 1, 1, "50-000"), "500400600", Arrays.asList("1", "2"),  100, LocalDateTime.now())
                        )
                )
                .flatMap(orderRepository::save)
                .subscribe();
    }


}
