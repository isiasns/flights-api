package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class ScheduledFlightServiceTest {
    @Mock
    private ApiUtil apiUtil;
    @Mock
    private AirlineService airlineService;
    @Mock
    private AirportService airportService;
    @TestSubject
    private ScheduledFlightService scheduledFlightService = new ScheduledFlightService(apiUtil, airlineService, airportService);

    @Test
    public void testGetScheduledFlightsFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/LAX/to/JFK/departing/2018/11/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        expect(apiUtil.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(apiUrl);
        Airline airline = new Airline();
        List<Airline> airlines = new ArrayList<>();
        airlines.add(airline);
        expect(airlineService.findByFsIn(anyObject())).andReturn(airlines);
        Airport airport = new Airport();
        List<Airport> airports = new ArrayList<>();
        airports.add(airport);
        expect(airportService.findByFsIn(anyObject())).andReturn(airports);
        replay(apiUtil);
        replay(airlineService);
        replay(airportService);
        scheduledFlightService.getScheduledFlightsFromApi("LAX", "JFK", "2018", "11", "01");
        verify(apiUtil);
        verify(airlineService);
        verify(airportService);
    }
}