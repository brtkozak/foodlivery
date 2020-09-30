package com.foodlivery.dishservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DishserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishserviceApplication.class, args);
	}

}
