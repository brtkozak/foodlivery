package com.foodlivery.dishservice.service;

import com.foodlivery.dishservice.model.Dish;
import com.foodlivery.dishservice.repository.DishRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Service
public class InitService {

    private DishRepository dishRepository;

    public InitService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mockDishes() {
        dishRepository.deleteAll()
                .thenMany(
                        Flux.just(new Dish("1", "1", "Pasta", 30.0, Arrays.asList("Pasta", "Oil")),
                                new Dish("2", "1", "Pizza", 35.0, Arrays.asList("Dough", "Cheese", "Meat", "Tomato sauce") ),
                                new Dish("3", "1", "Gnocchi", 40.0, Arrays.asList("Gnocchi")),
                                new Dish("4", "2", "Maki", 25.0, Arrays.asList("Cucumber", "Avocado")),
                                new Dish("5","2", "Nigiri", 28.0,  Arrays.asList("Wasabi", "Rice")),
                                new Dish("6","2", "Sugatazushi", 30.0, Arrays.asList("Nori")))
                ).flatMap(dishRepository::save)
                .subscribe();
    }

}