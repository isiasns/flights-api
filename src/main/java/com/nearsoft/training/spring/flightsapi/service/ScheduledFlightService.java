package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.search.ScheduleSearch;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import com.nearsoft.training.spring.flightsapi.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ScheduledFlightService {
    private ApiUtil apiUtil;
    private AirlineService airlineService;
    private AirportService airportService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public ScheduledFlightService(ApiUtil apiUtil, AirlineService airlineService, AirportService airportService) {
        this.apiUtil = apiUtil;
        this.airlineService = airlineService;
        this.airportService = airportService;
    }

    @Cacheable("scheduled-flighs")
    public List<ScheduledFlight> getScheduledFlightsFromApi(String apiUrl) throws IOException {
        String response = restTemplate.getForObject(apiUrl, String.class);
        JsonNode jsonNode = JsonUtil.getJsonCollection(response, "/scheduledFlights");
        ScheduledFlight[] scheduledFlights = new ObjectMapper().treeToValue(jsonNode, ScheduledFlight[].class);
        loadAirlines(Arrays.asList(scheduledFlights));
        loadAirports(Arrays.asList(scheduledFlights));
        return Arrays.asList(scheduledFlights).stream().sorted(
                Comparator.comparing(ScheduledFlight::getCarrierFsCode)
                        .thenComparing(ScheduledFlight::getDepartureTime))
                .collect(Collectors.toList());
    }

    private void loadAirlines(List<ScheduledFlight> scheduledFlights) {
        List<String> carrierFsCode = scheduledFlights.stream()
                .map(scheduledFlight -> scheduledFlight.getCarrierFsCode())
                .distinct().collect(toList());
        List<Airline> airlines = this.airlineService.findByFsIn(carrierFsCode);
        if (airlines != null && !airlines.isEmpty()) {
            for (ScheduledFlight scheduledFlight : scheduledFlights) {
                airlines.stream()
                        .filter(airline -> scheduledFlight.getCarrierFsCode().equals(airline.getFs()))
                        .findFirst().ifPresent(scheduledFlight::setAirline);
            }
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
        if (airports != null && !airports.isEmpty()) {
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

    public Map<String, List<ScheduledFlight>> getOneWayScheduledFlights(ScheduleSearch scheduleSearch) throws IOException {
        Map<String, List<ScheduledFlight>> scheduledFlights = new HashMap<>();
        ;
        List<ScheduledFlight> departingFlights = getScheduledFlights(scheduleSearch.getFrom(), scheduleSearch.getTo(),
                scheduleSearch.getDepartureYear(), scheduleSearch.getDepartureMonth(), scheduleSearch.getDepartureDay());
        scheduledFlights.put("departing", departingFlights);
        return scheduledFlights;
    }

    public Map<String, List<ScheduledFlight>> getRoundTripScheduledFlights(ScheduleSearch scheduleSearch) throws IOException {
        Map<String, List<ScheduledFlight>> scheduledFlights = getOneWayScheduledFlights(scheduleSearch);
        List<ScheduledFlight> returningFlights = getScheduledFlights(scheduleSearch.getTo(), scheduleSearch.getFrom(),
                scheduleSearch.getArrivalYear(), scheduleSearch.getArrivalMonth(), scheduleSearch.getArrivalDay());
        scheduledFlights.put("returning", returningFlights);
        return scheduledFlights;
    }

    public List<ScheduledFlight> getScheduledFlights(String from, String to, String year, String month, String day) throws IOException {
        Map<String, String> required = new LinkedHashMap<>();
        required.put("from", from);
        required.put("to", to);
        required.put("departing", year + "/" + month + "/" + day);
        Map<String, String> optional = new LinkedHashMap<>();
        optional.put("codeType", "FS");
        List<ScheduledFlight> departingFlights =
                getScheduledFlightsFromApi(apiUtil.getApiUrl("schedules", required, optional));
        return departingFlights;
    }
}
