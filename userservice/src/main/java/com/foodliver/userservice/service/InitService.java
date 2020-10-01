package com.foodliver.userservice.service;

import com.foodliver.userservice.model.Address;
import com.foodliver.userservice.model.User;
import com.foodliver.userservice.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InitService {

    private UserRepository userRepository;

    public InitService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mockUsers() {
        userRepository.deleteAll()
                .thenMany(
                        Flux.just(
                                new User("1", "user1@gmail.com", "password", "first1", "last1", new Address("Wroclove", "Pilsudskiego", 1, 2, "50-500"), "123456789"),
                                new User("2", "user2@gmail.com", "password", "first2", "last2", new Address("Warsaw", "Hermana", 1, 2, "50-500"), "123456789"),
                                new User("3", "user3@gmail.com", "password", "first3", "last3", new Address("Cracow", "Kazimierza Wielkiego", 1, 2, "50-500"), "123456789"),
                                new User("4", "user4@gmail.com", "password", "first4", "last4", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789"),
                                new User("5", "user5@gmail.com", "password", "first5", "last5", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789"),
                                new User("6", "user6@gmail.com", "password", "first6", "last6", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789"),
                                new User("7", "user7@gmail.com", "password", "first7", "last7", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789"),
                                new User("8", "user8@gmail.com", "password", "first8", "last8", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789"),
                                new User("9", "user9@gmail.com", "password", "first9", "last9", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789"),
                                new User("10", "user10@gmail.com", "password", "first10", "last10", new Address("Warsaw", "Bolesława Śmiałego", 1, 2, "50-500"), "123456789")
                        )
                ).flatMap(userRepository::save)
                .subscribe();
    }

}
