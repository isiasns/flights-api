package com.nearsoft.training.spring.flightsapi.repository;

import com.nearsoft.training.spring.flightsapi.model.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AirportRepository extends MongoRepository<Airport, String> {
    List<Airport> findByFsIn(List<String> airportCodes);
}
