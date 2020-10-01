package com.foodlivery.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class Order {
    @Id
    String id;
    @NotEmpty(message = "Customer id can't be empty")
    String customerId;
    @NotEmpty(message = "Restaurant id can't be empty")
    String restaurantId;
    @NotNull(message = "Address can't be empty")
    Address address;
    @NotEmpty(message = "Phone number can't be empty")
    String phoneNo;
    @NotNull(message = "Dishes ids can't be empty")
    List<String> dishesIds;
    double totalPrice;
    LocalDate createdDate = LocalDate.now();
}
