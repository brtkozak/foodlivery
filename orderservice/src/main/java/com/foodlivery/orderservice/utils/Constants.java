package com.foodlivery.orderservice.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String BASE_ENDPOINT = "/order";
    public static String PATH_VARIABLE_ORDER_ID = "orderId";
    public static String PATH_VARIABLE_USER_ID = "userId";
    public static String SERVICE_PATH_DISH = "dishservice/dish";
}
