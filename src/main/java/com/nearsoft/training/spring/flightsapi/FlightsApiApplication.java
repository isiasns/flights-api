package com.nearsoft.training.spring.flightsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FlightsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsApiApplication.class, args);
    }
}
