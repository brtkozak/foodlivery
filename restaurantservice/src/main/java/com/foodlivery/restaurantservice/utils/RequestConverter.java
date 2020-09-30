package com.foodlivery.restaurantservice.utils;

import com.foodlivery.restaurantservice.model.reponse.PageResponse;
import com.google.common.primitives.Ints;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Arrays;
import java.util.List;

@Component
public class RequestConverter {

    public static Pageable getPageable(ServerRequest request) {
        int pageNumber = request.queryParam(Constants.QUERY_PAGE).map(Ints::tryParse).orElse(PageResponse.FIRST_PAGE);
        int size = request.queryParam(Constants.QUERY_SIZE).map(Ints::tryParse).orElse(PageResponse.DEFAULT_PAGE_SIZE);
        return PageRequest.of(pageNumber, size);
    }

    public static List<String> getValuesFromQueryParameter(ServerRequest request, String param){
        String params = request.queryParam(param).orElse(null);
        if(params == null)
            return null;
       return Arrays.asList((params).split(","));
    }

}
