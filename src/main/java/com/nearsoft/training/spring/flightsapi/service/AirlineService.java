package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airlines;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class AirlineService {
    private AirlineRepository airlineRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public List<Airline> saveAll(List<Airline> airlines) {
        return this.airlineRepository.saveAll(airlines);
    }

    public List<Airline> getAllAirlinesFromApi(String apiUrl) throws IOException {
        String response = restTemplate.getForObject(apiUrl, String.class);
        Airlines airlines = new ObjectMapper().readValue(response, Airlines.class);
        return airlines.getAirlines();
    }

    public List<Airline> findByFsIn(List<String> fs) {
        return this.airlineRepository.findByFsIn(fs);
    }

    @Cacheable("airlines")
    public List<Airline> findAll() {
        return this.airlineRepository.findAll();
    }
}
