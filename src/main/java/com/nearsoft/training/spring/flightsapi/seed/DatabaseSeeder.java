package com.nearsoft.training.spring.flightsapi.seed;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.service.AirlineService;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DatabaseSeeder {
    private AirportService airportService;
    private AirlineService airlineService;

    public DatabaseSeeder(AirportService airportService, AirlineService airlineService) {
        this.airportService = airportService;
        this.airlineService = airlineService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) throws IOException {
        seedAirlines();
        seedAirports();
    }

    private void seedAirlines() throws IOException {
        List<Airline> airlines = airlineService.getAirlinesFromApi();
        airlineService.saveAll(airlines);
    }

    private void seedAirports() throws IOException {
        List<Airport> airports = airportService.getAirportsFromApi();
        airportService.saveAll(airports);
    }
}
