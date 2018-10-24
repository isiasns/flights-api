package com.nearsoft.training.spring.flightsapi.seed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.model.Airlines;
import com.nearsoft.training.spring.flightsapi.model.Airports;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseSeeder {
    private AirportRepository airportRepository;
    private AirlineRepository airlineRepository;
    private RestTemplate restTemplate = new RestTemplate();
    private FlightsApiConfiguration flightsApiConfiguration;

    public DatabaseSeeder(AirportRepository airportRepository, AirlineRepository airlineRepository, FlightsApiConfiguration flightsApiConfiguration) {
        this.airportRepository = airportRepository;
        this.airlineRepository = airlineRepository;
        this.flightsApiConfiguration = flightsApiConfiguration;
    }

    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) throws IOException {
        seedAirlines();
        seedAirports();
    }

    private void seedAirlines() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("active","");
        String response = restTemplate.getForObject(flightsApiConfiguration.getApiUrl("airlines", params), String.class);
        Airlines airlines = new ObjectMapper().readValue(response, Airlines.class);
        airlineRepository.saveAll(Arrays.asList(airlines.getAirlines()));
    }

    private void seedAirports() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("active","");
        String response = restTemplate.getForObject(flightsApiConfiguration.getApiUrl("airports", params), String.class);
        Airports airports = new ObjectMapper().readValue(response, Airports.class);
        airportRepository.saveAll(Arrays.asList(airports.getAirports()));
    }
}
