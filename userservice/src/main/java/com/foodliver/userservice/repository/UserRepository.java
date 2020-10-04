package com.foodliver.userservice.repository;

import com.foodliver.userservice.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findById(String id);
    Mono<User> findByUsername(String userName);
}
