package com.foodlivery.restaurantservice.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String BASE_ENDPOINT = "/restaurant";
    public static String PATH_VARIABLE_RESTAURANT_ID = "restaurantId";
    public static String QUERY_PAGE = "page";
    public static String QUERY_SIZE = "size";
    public static String QUERY_NAME = "name";
    public static String QUERY_CATEGORY = "category";
    public static String QUERY_CITY = "city";

    public static String SERVICE_PATH_DISH = "dishservice/dish";
}
