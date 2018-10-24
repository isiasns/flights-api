package com.nearsoft.training.spring.flightsapi.repository;

import com.nearsoft.training.spring.flightsapi.model.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirportRepository extends MongoRepository<Airport, String> {
}
