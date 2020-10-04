package com.foodliver.userservice.utils;

import com.foodliver.userservice.model.Dish;
import com.foodliver.userservice.model.dto.OrderDto;
import com.foodliver.userservice.model.response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public static OrderResponse getOrderResponse(OrderDto orderDto, List<Dish> dishes) {
        for(Dish dish : dishes) {
            dish.setQuantity((int) orderDto.getDishesIds().stream().filter(it -> it.equals(dish.getId())).count());
        }
        return new OrderResponse(
                orderDto.getId(),
                orderDto.getCustomerId(),
                orderDto.getRestaurantId(),
                orderDto.getAddress(),
                orderDto.getPhoneNo(),
                dishes,
                orderDto.getTotalPrice(),
                orderDto.getCreatedDate()
        );
    }

}
