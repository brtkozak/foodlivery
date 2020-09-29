package com.foodliver.userservice.handler;

import com.foodliver.userservice.model.User;
import com.foodliver.userservice.model.request.UserRequest;
import com.foodliver.userservice.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class UserHandler implements ReactiveUserDetailsService {

    private UserRepository userRepository;
    private RequestValidator requestValidator;
    private UserConverter userConverter;

    public UserHandler(UserRepository userRepository, RequestValidator requestValidator, UserConverter userConverter){
        this.userRepository = userRepository;
        this.requestValidator = requestValidator;
        this.userConverter = userConverter;
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return userRepository.findById(request.pathVariable("userId"))
                .map(userConverter::userToUserResponse)
                .flatMap(user ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return userRepository.findAll()
                .collectList()
                .flatMap(users ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(users), User.class));
    }

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(requestValidator::validate)
                .map(userConverter::userRequestToUser)
                .flatMap(userRepository::insert)
                .map(userConverter::userToUserResponse)
                .flatMap(it -> ServerResponse.created(URI.create("http://localhoost:8080/user/" + it.getId())).contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), User.class));
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(requestValidator::validate)
                .map(userConverter::userRequestToUser)
                .map(user -> {
                    user.setId(request.pathVariable("userId"));
                    return user;
                })
                .flatMap(userRepository::save)
                .map(userConverter::userToUserResponse)
                .flatMap(it -> ServerResponse.created(URI.create("http://localhoost:8080/user/" + it.getId())).contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), User.class));
    }

    @Override
    public Mono<UserDetails> findByUsername(String userName) {
        return userRepository.findByUsername(userName)
                .map(it -> (UserDetails) it)
                .map(it -> {
                    if(it == null){
                        throw new UsernameNotFoundException("User" + userName + "does not exist");
                    }
                    else {
                        return it;
                    }
                });
    }

}
