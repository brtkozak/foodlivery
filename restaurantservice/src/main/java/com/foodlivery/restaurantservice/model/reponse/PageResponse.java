package com.foodlivery.restaurantservice.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {

    public static final int FIRST_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    int pageNumber;
    int pageSize;
    int totalElements;
    private List<T> items;

}
