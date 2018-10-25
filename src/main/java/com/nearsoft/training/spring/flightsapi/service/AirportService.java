package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.model.Airports;
import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirportService {
    private AirportRepository airportRepository;
    private FlightsApiConfiguration flightsApiConfiguration;

    public AirportService(AirportRepository airportRepository, FlightsApiConfiguration flightsApiConfiguration) {
        this.airportRepository = airportRepository;
        this.flightsApiConfiguration = flightsApiConfiguration;
    }

    public List<Airport> saveAll(List<Airport> airports) {
        return this.airportRepository.saveAll(airports);
    }

    public List<Airport> getAllAirportsFromApi() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("active", "");
        String response = new RestTemplate().getForObject(flightsApiConfiguration.getApiUrl("airports", params), String.class);
        Airports airports = new ObjectMapper().readValue(response, Airports.class);
        return Arrays.asList(airports.getAirports());
    }
}
