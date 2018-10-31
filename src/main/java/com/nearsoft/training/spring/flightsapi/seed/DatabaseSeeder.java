package com.nearsoft.training.spring.flightsapi.seed;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.service.AirlineService;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseSeeder {
    private AirportService airportService;
    private AirlineService airlineService;
    private ApiUtil apiUtil;

    @Autowired
    public DatabaseSeeder(AirportService airportService, AirlineService airlineService, ApiUtil apiUtil) {
        this.airportService = airportService;
        this.airlineService = airlineService;
        this.apiUtil = apiUtil;
    }

    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) throws IOException {
        seedAirlines();
        seedAirports();
    }

    private void seedAirlines() throws IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("active", "");
        List<Airline> airlines = airlineService.getAllAirlinesFromApi(apiUtil.getApiUrl("airlines", params));
        airlineService.saveAll(airlines);
    }

    private void seedAirports() throws IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("active", "");
        List<Airport> airports = airportService.getAllAirportsFromApi(apiUtil.getApiUrl("airports", params));
        airportService.saveAll(airports);
    }
}
