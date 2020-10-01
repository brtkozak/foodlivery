package com.foodlivery.ratingservice.service;


import com.foodlivery.ratingservice.model.Rating;
import com.foodlivery.ratingservice.repository.RatingRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class InitService {

    private RatingRepository ratingRepository;

    public InitService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mockRatings(){
        ratingRepository
                .deleteAll()
                .thenMany(
                        Flux.just(
                                new Rating("1", "1", "1", 4, ""),
                                new Rating("2", "1", "2", 1, ""),
                                new Rating("3", "1", "3", 2, ""),
                                new Rating("4", "1", "4", 5, ""),
                                new Rating("5", "1", "5", 5, ""),
                                new Rating("6", "1", "6", 3, ""),
                                new Rating("7", "2", "7", 5, ""),
                                new Rating("8", "2", "8", 4, ""),
                                new Rating("9", "2", "9", 5, ""),
                                new Rating("10", "2", "10", 5, "")
                        )
                )
                .flatMap(ratingRepository::save)
                .subscribe();
    }

}
