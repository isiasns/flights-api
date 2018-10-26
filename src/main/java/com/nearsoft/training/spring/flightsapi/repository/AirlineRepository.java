package com.nearsoft.training.spring.flightsapi.repository;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AirlineRepository extends MongoRepository<Airline, String> {
    List<Airline> findByFsIn(List<String> fs);
}
