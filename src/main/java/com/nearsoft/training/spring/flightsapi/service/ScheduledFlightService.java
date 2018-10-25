package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlights;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class ScheduledFlightService {
    private FlightsApiConfiguration flightsApiConfiguration;

    public ScheduledFlightService(FlightsApiConfiguration flightsApiConfiguration) {
        this.flightsApiConfiguration = flightsApiConfiguration;
    }

    @Cacheable("scheduled-flighs")
    public List<ScheduledFlight> getScheduledFlightsFromApi(String from, String to, String year, String month, String day) throws IOException {
        Map<String, String> required = new LinkedHashMap<>();
        required.put("from", from);
        required.put("to", to);
        required.put("departing", year + "/" + month + "/" + day);
        Map<String, String> optional = new LinkedHashMap<>();
        optional.put("codeType", "FS");
        String response = new RestTemplate().getForObject(flightsApiConfiguration.getApiUrl("schedules", required, optional), String.class);
        ScheduledFlights scheduledFlights = new ObjectMapper().readValue(response, ScheduledFlights.class);
        return Arrays.asList(scheduledFlights.getScheduledFlights());
    }
}
