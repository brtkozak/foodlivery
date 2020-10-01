package com.foodlivery.ratingservice.service;

import com.foodlivery.ratingservice.router.ClientException;
import com.foodlivery.ratingservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService extends BaseService {

    public UserService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public Mono<String> getUser(String userId) {
        return webClient
                .get()
                .uri("http://" + Constants.SERVICE_PATH_USER + "/" + userId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new ClientException("User with id " + userId + " not found.", response)))
                .bodyToMono(String.class);
    }

}
