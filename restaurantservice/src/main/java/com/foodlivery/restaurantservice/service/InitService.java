package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.Address;
import com.foodlivery.restaurantservice.model.Restaurant;
import com.foodlivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import java.util.Arrays;
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
                        Flux.just(new Restaurant("1", "Pizzeria", new Address("Wroclove", "Pilsudskiego", 1, 2, "50-500"), Arrays.asList(Restaurant.Category.ITALIAN, Restaurant.Category.EUROPEAN), 29d),
                                new Restaurant("2", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("3", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("4", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("5", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("6", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("7", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("8", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("9", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("10", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("11", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("12", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("13", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("14", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("15", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("16", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("17", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("18", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("19", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d),
                                new Restaurant("20", "Sushi Wroclove", new Address("Wroclove", "Kazimierza Wielkego", 2, 3, "50-500"), new ArrayList<Restaurant.Category>(Collections.singleton(Restaurant.Category.SUSHI)), 0d))
                ).flatMap(restaurantRepository::save)
                .subscribe();
    }

}
