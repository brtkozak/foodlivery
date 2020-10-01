package com.foodlivery.restaurantservice.service;

import com.foodlivery.restaurantservice.model.SimpleRating;
import com.foodlivery.restaurantservice.model.User;
import com.foodlivery.restaurantservice.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService extends BaseService {

    public UserService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<User> getUser(String userId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_USER + "/" + userId)
                .retrieve()
                .bodyToMono(User.class);
    }

}
