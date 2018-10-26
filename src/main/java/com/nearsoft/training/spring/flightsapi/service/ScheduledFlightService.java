package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlights;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class ScheduledFlightService {
    private ApiUtil apiUtil;
    private AirlineService airlineService;
    private AirportService airportService;

    public ScheduledFlightService(ApiUtil apiUtil, AirlineService airlineService, AirportService airportService) {
        this.apiUtil = apiUtil;
        this.airlineService = airlineService;
        this.airportService = airportService;
    }

    @Cacheable("scheduled-flighs")
    public List<ScheduledFlight> getScheduledFlightsFromApi(String from, String to, String year, String month, String day) throws IOException {
        Map<String, String> required = new LinkedHashMap<>();
        required.put("from", from);
        required.put("to", to);
        required.put("departing", year + "/" + month + "/" + day);
        Map<String, String> optional = new LinkedHashMap<>();
        optional.put("codeType", "FS");
        String response = new RestTemplate().getForObject(apiUtil.getApiUrl("schedules", required, optional), String.class);
        ScheduledFlights scheduledFlights = new ObjectMapper().readValue(response, ScheduledFlights.class);
        loadAirlines(Arrays.asList(scheduledFlights.getScheduledFlights()));
        loadAirports(Arrays.asList(scheduledFlights.getScheduledFlights()));
        return Arrays.asList(scheduledFlights.getScheduledFlights());
    }

    private void loadAirlines(List<ScheduledFlight> scheduledFlights) {
        List<String> carrierFsCode = scheduledFlights.stream()
                .map(scheduledFlight -> scheduledFlight.getCarrierFsCode())
                .distinct().collect(toList());
        List<Airline> airlines = this.airlineService.findByFsIn(carrierFsCode);
        for (ScheduledFlight scheduledFlight : scheduledFlights) {
            airlines.stream()
                    .filter(airline -> scheduledFlight.getCarrierFsCode().equals(airline.getFs()))
                    .findFirst().ifPresent(scheduledFlight::setAirline);
        }
    }

    private void loadAirports(List<ScheduledFlight> scheduledFlights) {
        List<String> airportCodes = scheduledFlights.stream()
                .map(scheduledFlight -> scheduledFlight.getArrivalAirportFsCode())
                .distinct().collect(toList());
        airportCodes.addAll(scheduledFlights.stream()
                .map(scheduledFlight -> scheduledFlight.getDepartureAirportFsCode())
                .distinct().collect(toList()));
        airportCodes = airportCodes.stream().distinct().collect(toList());
        List<Airport> airports = this.airportService.findByFsIn(airportCodes);
        for (ScheduledFlight scheduledFlight : scheduledFlights) {
            airports.stream()
                    .filter(airport -> scheduledFlight.getArrivalAirportFsCode().equals(airport.getFs()))
                    .findFirst().ifPresent(scheduledFlight::setArrivalAirport);
            airports.stream()
                    .filter(airport -> scheduledFlight.getDepartureAirportFsCode().equals(airport.getFs()))
                    .findFirst().ifPresent(scheduledFlight::setDepartureAirport);
        }
    }
}
