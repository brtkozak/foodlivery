package com.foodliver.userservice.service;

import com.foodliver.userservice.model.dto.OrderDto;
import com.foodliver.userservice.model.response.OrderResponse;
import com.foodliver.userservice.utils.Constants;
import com.foodliver.userservice.utils.OrderConverter;
import com.foodliver.userservice.utils.RequestValidator;
import com.foodliver.userservice.utils.UserConverter;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class UserService implements ReactiveUserDetailsService {

    private UserRepository userRepository;
    private RequestValidator requestValidator;
    private UserConverter userConverter;
    private OrderService orderService;
    private DishService dishService;

    public UserService(UserRepository userRepository, RequestValidator requestValidator, UserConverter userConverter, OrderService orderService, DishService dishService){
        this.userRepository = userRepository;
        this.requestValidator = requestValidator;
        this.userConverter = userConverter;
        this.orderService = orderService;
        this.dishService = dishService;
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return userRepository.findById(request.pathVariable(Constants.PATH_VARIABLE_USER_ID))
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
                .flatMap(it -> ServerResponse.created(URI.create(request.uri().toString() + "/" + it.getId())).contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), User.class));
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(requestValidator::validate)
                .map(userConverter::userRequestToUser)
                .map(user -> {
                    user.setId(request.pathVariable(Constants.PATH_VARIABLE_USER_ID));
                    return user;
                })
                .flatMap(userRepository::save)
                .map(userConverter::userToUserResponse)
                .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), User.class));
    }

    public Mono<ServerResponse> getOrders(ServerRequest request) {
        String userId = request.pathVariable(Constants.PATH_VARIABLE_USER_ID);
        Mono<OrderDto> orderDto = orderService.getOrdersForUser(userId);
        return orderDto.flatMap(order ->
                Flux.fromIterable(order.getDishesIds())
                        .flatMap(dishId -> dishService.getDish(dishId))
                        .collectList()
                        .map(it -> OrderConverter.getOrderResponse(order, it))
                        .flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), OrderResponse.class)));
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
