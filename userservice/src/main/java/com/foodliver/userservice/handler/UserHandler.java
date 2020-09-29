package com.foodliver.userservice.handler;

import com.foodliver.userservice.model.User;
import com.foodliver.userservice.model.UserResponse;
import com.foodliver.userservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.net.URI;

@Service
public class UserHandler implements ReactiveUserDetailsService {

    private UserRepository userRepository;
    private RequestValidator requestValidator;

    public UserHandler(UserRepository userRepository, RequestValidator requestValidator){
        this.userRepository = userRepository;
        this.requestValidator = requestValidator;
    }

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return userRepository.findById(request.pathVariable("userId"))
                .map(UserResponse::userToUserResponse)
                .flatMap(user ->
                        ServerResponse.ok().body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return userRepository.findAll()
                .collectList()
                .flatMap(users ->
                        ServerResponse.ok().body(Mono.just(users), User.class));
    }

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(requestValidator::validate)
                .map(user -> {
                    user.setPassword(passwordEncoder().encode(user.getPassword()));
                    return user;
                })
                .flatMap(it -> userRepository.save(it))
                .map(UserResponse::userToUserResponse)
                .flatMap(it -> ServerResponse.created(URI.create("http://localhoost:8080/user/" + it.getId())).body(Mono.just(it), User.class))
                .onErrorResume(error ->
                        ServerResponse.badRequest().body(Mono.just(error.getMessage()), String.class));
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
