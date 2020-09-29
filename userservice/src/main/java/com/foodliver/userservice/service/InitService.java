package com.foodliver.userservice.service;

import com.foodliver.userservice.model.Address;
import com.foodliver.userservice.model.User;
import com.foodliver.userservice.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InitService {

    private UserRepository userRepository;

    public InitService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener (ApplicationReadyEvent.class)
    public void mockUsers() {

        userRepository.deleteAll()
                .then(
                        Mono.just(new User("0", "user1@gmail.com", "password", "first", "last", new Address("Wroclove", "Pilsudskiego", 1, 2, "50-500"), "123456789"))
                ).flatMap(userRepository::save)
                .subscribe();
    }

}
