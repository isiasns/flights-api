package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.search.ScheduleSearch;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @Test
    public void testGetOneWayScheduledFlights() throws IOException {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("LAX").to("JFK").departureYear("2018").departureMonth("11")
                .departureDay("01").build();
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
        Map<String, List<ScheduledFlight>> scheduledFlights = scheduledFlightService.getOneWayScheduledFlights(scheduleSearch);
        assertThat(scheduledFlights.isEmpty(), is(false));
        verify(apiUtil);
        verify(airlineService);
        verify(airportService);
    }

    @Test
    public void testGetRoundTripScheduledFlights() throws IOException {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("LAX").to("JFK").departureYear("2018").departureMonth("11")
                .departureDay("01").arrivalYear("2018").arrivalMonth("12").arrivalDay("01").build();
        String departureApiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/LAX/to/JFK/departing/2018/11/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        expect(apiUtil.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(departureApiUrl);
        String arrivalApiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/JFK/to/LAX/departing/2018/12/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        expect(apiUtil.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(arrivalApiUrl);
        Airline airline = new Airline();
        List<Airline> airlines = new ArrayList<>();
        airlines.add(airline);
        expect(airlineService.findByFsIn(anyObject())).andReturn(airlines).times(2);
        Airport airport = new Airport();
        List<Airport> airports = new ArrayList<>();
        airports.add(airport);
        expect(airportService.findByFsIn(anyObject())).andReturn(airports).times(2);
        replay(apiUtil);
        replay(airlineService);
        replay(airportService);
        Map<String, List<ScheduledFlight>> scheduledFlights = scheduledFlightService.getRoundTripScheduledFlights(scheduleSearch);
        assertThat(scheduledFlights.isEmpty(), is(false));
        verify(apiUtil);
        verify(airlineService);
        verify(airportService);
    }
}