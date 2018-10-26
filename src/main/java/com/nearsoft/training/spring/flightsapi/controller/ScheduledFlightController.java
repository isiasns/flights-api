package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.search.ScheduleSearch;
import com.nearsoft.training.spring.flightsapi.service.ScheduledFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class ScheduledFlightController {
    private ScheduledFlightService scheduledFlightService;

    @Autowired
    public ScheduledFlightController(ScheduledFlightService scheduledFlightService) {
        this.scheduledFlightService = scheduledFlightService;
    }

    @GetMapping("/one-way")
    public ResponseEntity<Map<String, List<ScheduledFlight>>> getScheduledFlightsOneWay(ScheduleSearch scheduleSearch) throws IOException {
        return new ResponseEntity<>(this.scheduledFlightService.getOneWayScheduledFlights(scheduleSearch), HttpStatus.OK);
    }

    @GetMapping("/round-trip")
    public ResponseEntity<Map<String, List<ScheduledFlight>>> getScheduledFlightsRoundTrip(ScheduleSearch scheduleSearch) throws IOException {
        return new ResponseEntity<>(this.scheduledFlightService.getRoundTripScheduledFlights(scheduleSearch), HttpStatus.OK);
    }
}
