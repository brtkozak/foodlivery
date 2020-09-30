package com.foodliver.userservice.utils;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class RequestValidator {

    private Validator validator;

    public RequestValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validate(T obj) {

        if (obj == null) {
            return Mono.error(new IllegalArgumentException());
        }

        Set<ConstraintViolation<T>> violations = this.validator.validate(obj);
        if (violations == null || violations.isEmpty()) {
            return Mono.just(obj);
        }

        return Mono.error(new ConstraintViolationException(violations));
    }
}