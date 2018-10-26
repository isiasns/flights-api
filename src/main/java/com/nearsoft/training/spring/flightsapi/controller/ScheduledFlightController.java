package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.search.ScheduleSearch;
import com.nearsoft.training.spring.flightsapi.service.ScheduledFlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class ScheduledFlightController {
    private ScheduledFlightService scheduledFlightService;

    public ScheduledFlightController(ScheduledFlightService scheduledFlightService) {
        this.scheduledFlightService = scheduledFlightService;
    }

    @GetMapping("/one-way")
    public ResponseEntity<List<ScheduledFlight>> getScheduledFlightsOneWay(ScheduleSearch scheduleSearch) throws IOException {
        List<ScheduledFlight> scheduledFlights =
                scheduledFlightService.getScheduledFlightsFromApi(scheduleSearch.getFrom(), scheduleSearch.getTo(),
                        scheduleSearch.getDepartureYear(), scheduleSearch.getDepartureMonth(), scheduleSearch.getDepartureDay());
        return new ResponseEntity<>(scheduledFlights, HttpStatus.OK);
    }

    @GetMapping("/round-trip")
    public ResponseEntity<Map<String, List<ScheduledFlight>>> getScheduledFlightsRoundTrip(ScheduleSearch scheduleSearch) throws IOException {
        List<ScheduledFlight> departingFlights =
                scheduledFlightService.getScheduledFlightsFromApi(scheduleSearch.getFrom(), scheduleSearch.getTo(),
                        scheduleSearch.getDepartureYear(), scheduleSearch.getDepartureMonth(), scheduleSearch.getDepartureDay());
        List<ScheduledFlight> returningFlights =
                scheduledFlightService.getScheduledFlightsFromApi(scheduleSearch.getTo(), scheduleSearch.getFrom(),
                        scheduleSearch.getArrivalYear(), scheduleSearch.getArrivalMonth(), scheduleSearch.getArrivalDay());
        Map<String, List<ScheduledFlight>> scheduledFlights = new HashMap<>();
        scheduledFlights.put("departing", departingFlights);
        scheduledFlights.put("returning", returningFlights);
        return new ResponseEntity<>(scheduledFlights, HttpStatus.OK);
    }
}
