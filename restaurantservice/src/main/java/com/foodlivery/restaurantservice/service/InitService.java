package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.Address;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class InitService {

    private RestaurantRepository restaurantRepository;

    public InitService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mockRestaurants() {


        restaurantRepository.deleteAll()
                .thenMany(
                        Flux.just(new Restaurant("1", "Pizzeria", new Address("Wroclove", "Pilsudskiego", 1, 2, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.ITALIAN)), 29d),
                                new Restaurant("2", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d))
                ).flatMap(restaurantRepository::save)
                .subscribe();
    }

}
