package com.foodliver.userservice.utils;

import com.foodliver.userservice.model.Dish;
import com.foodliver.userservice.model.dto.OrderDto;
import com.foodliver.userservice.model.response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderConverter {

    public static OrderResponse getOrderResponse(OrderDto orderDto, List<Dish> dishes) {
        return new OrderResponse(
                orderDto.getId(),
                orderDto.getCustomerId(),
                orderDto.getRestaurantId(),
                orderDto.getAddress(),
                orderDto.getPhoneNo(),
                dishes,
                orderDto.getCreatedDate());
    }

}
