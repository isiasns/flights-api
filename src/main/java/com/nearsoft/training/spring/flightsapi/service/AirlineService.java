package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airlines;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirlineService {
    private AirlineRepository airlineRepository;
    private FlightsApiConfiguration flightsApiConfiguration;

    public AirlineService(AirlineRepository airlineRepository, FlightsApiConfiguration flightsApiConfiguration) {
        this.airlineRepository = airlineRepository;
        this.flightsApiConfiguration = flightsApiConfiguration;
    }

    public List<Airline> saveAll(List<Airline> airlines) {
        return this.airlineRepository.saveAll(airlines);
    }

    public List<Airline> getAirlinesFromApi() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("active", "");
        String response = new RestTemplate().getForObject(flightsApiConfiguration.getApiUrl("airlines", params), String.class);
        Airlines airlines = new ObjectMapper().readValue(response, Airlines.class);
        return Arrays.asList(airlines.getAirlines());
    }
}
