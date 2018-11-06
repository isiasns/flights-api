package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import com.nearsoft.training.spring.flightsapi.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class AirportService {
    private AirportRepository airportRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> saveAll(List<Airport> airports) {
        return this.airportRepository.saveAll(airports);
    }

    public List<Airport> getAllAirportsFromApi(String apiUrl) throws IOException {
        String response = restTemplate.getForObject(apiUrl, String.class);
        JsonNode jsonNode = JsonUtil.getJsonCollection(response, "/airports");
        Airport[] airports = new ObjectMapper().treeToValue(jsonNode, Airport[].class);
        return Arrays.asList(airports);
    }

    public List<Airport> findByFsIn(List<String> airportCodes) {
        return this.airportRepository.findByFsIn(airportCodes);
    }

    @Cacheable("airports")
    public List<Airport> findAll() {
        return this.airportRepository.findAll();
    }
}
