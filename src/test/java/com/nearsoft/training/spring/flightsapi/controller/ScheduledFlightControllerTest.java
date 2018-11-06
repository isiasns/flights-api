package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.search.ScheduleSearch;
import com.nearsoft.training.spring.flightsapi.service.ScheduledFlightService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(EasyMockRunner.class)
public class ScheduledFlightControllerTest {
    @Mock
    private ScheduledFlightService scheduledFlightService;
    @TestSubject
    private ScheduledFlightController scheduledFlightController = new ScheduledFlightController(scheduledFlightService);

    @Test
    public void testGetScheduledFlightsOneWay() throws Exception {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("CUU").to("HMO")
                .departureYear("2018").departureMonth("12").departureDay("01").build();
        Map<String, List<ScheduledFlight>> flights = new HashMap<>();
        List<ScheduledFlight> scheduledFlights = new ArrayList<>();
        ScheduledFlight scheduledFlight = ScheduledFlight.builder().flightNumber("826").carrierFsCode("LCT").departureAirportFsCode("CUU").arrivalAirportFsCode("HMO")
                .stops(0).departureTime("2018-12-01T16:30:00.000").arrivalTime("2018-12-01T17:50:00.000").airline(Airline.builder().name("TAR Aerolineas").fs("LCT").build())
                .departureAirport(Airport.builder().name("Gen Fierro Villalobos Airport").city("Chihuahua").countryName("Mexico").fs("CUU").build())
                .arrivalAirport(Airport.builder().name("Gen Pesqueira Garcia Airport").city("Hermosillo").countryName("Mexico").fs("HMO").build()).build();
        scheduledFlights.add(scheduledFlight);
        flights.put("departing", scheduledFlights);
        expect(scheduledFlightService.getOneWayScheduledFlights(scheduleSearch)).andReturn(flights);
        replay(scheduledFlightService);
        ResponseEntity<Map<String, List<ScheduledFlight>>> result = scheduledFlightController.getScheduledFlightsOneWay(scheduleSearch);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), notNullValue());
        assertThat(result.getBody().size(), equalTo(1));
        assertThat(result.getBody().containsKey("departing"), equalTo(true));
        assertThat(result.getBody().get("departing").get(0).getAirline(), notNullValue());
        assertThat(result.getBody().get("departing").get(0).getAirline().getFs(), equalTo("LCT"));
        assertThat(result.getBody().get("departing").get(0).getDepartureAirport(), notNullValue());
        assertThat(result.getBody().get("departing").get(0).getDepartureAirport().getFs(), equalTo("CUU"));
        assertThat(result.getBody().get("departing").get(0).getArrivalAirport(), notNullValue());
        assertThat(result.getBody().get("departing").get(0).getArrivalAirport().getFs(), equalTo("HMO"));
        verify(scheduledFlightService);
    }

    @Test
    public void testGetScheduledFlightsRoundTrip() throws Exception {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("CUU").to("HMO")
                .departureYear("2018").departureMonth("12").departureDay("01")
                .arrivalYear("2019").arrivalMonth("01").arrivalDay("01").build();
        Map<String, List<ScheduledFlight>> flights = new HashMap<>();
        List<ScheduledFlight> departingFlights = new ArrayList<>();
        ScheduledFlight returningFlight = ScheduledFlight.builder().flightNumber("826").carrierFsCode("LCT").departureAirportFsCode("CUU").arrivalAirportFsCode("HMO")
                .stops(0).departureTime("2018-12-01T16:30:00.000").arrivalTime("2018-12-01T17:50:00.000").airline(Airline.builder().name("TAR Aerolineas").fs("LCT").build())
                .departureAirport(Airport.builder().name("Gen Fierro Villalobos Airport").city("Chihuahua").countryName("Mexico").fs("CUU").build())
                .arrivalAirport(Airport.builder().name("Gen Pesqueira Garcia Airport").city("Hermosillo").countryName("Mexico").fs("HMO").build()).build();
        departingFlights.add(returningFlight);
        flights.put("departing", departingFlights);
        List<ScheduledFlight> returningFlights = new ArrayList<>();
        ScheduledFlight departingFlight = ScheduledFlight.builder().flightNumber("827").carrierFsCode("LCT").departureAirportFsCode("HMO").arrivalAirportFsCode("CUU")
                .stops(0).departureTime("2019-01-01T18:30:00.000").arrivalTime("2019-01-01T19:50:00.000").airline(Airline.builder().name("TAR Aerolineas").fs("LCT").build())
                .departureAirport(Airport.builder().name("Gen Pesqueira Garcia Airport").city("Hermosillo").countryName("Mexico").fs("HMO").build())
                .arrivalAirport(Airport.builder().name("Gen Fierro Villalobos Airport").city("Chihuahua").countryName("Mexico").fs("CUU").build()).build();
        returningFlights.add(departingFlight);
        flights.put("returning", returningFlights);
        expect(scheduledFlightService.getOneWayScheduledFlights(scheduleSearch)).andReturn(flights);
        replay(scheduledFlightService);
        ResponseEntity<Map<String, List<ScheduledFlight>>> result = scheduledFlightController.getScheduledFlightsOneWay(scheduleSearch);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), notNullValue());
        assertThat(result.getBody().size(), equalTo(2));
        assertThat(result.getBody().containsKey("departing"), equalTo(true));
        assertThat(result.getBody().get("departing").get(0).getAirline(), notNullValue());
        assertThat(result.getBody().get("departing").get(0).getAirline().getFs(), equalTo("LCT"));
        assertThat(result.getBody().get("departing").get(0).getDepartureAirport(), notNullValue());
        assertThat(result.getBody().get("departing").get(0).getDepartureAirport().getFs(), equalTo("CUU"));
        assertThat(result.getBody().get("departing").get(0).getArrivalAirport(), notNullValue());
        assertThat(result.getBody().get("departing").get(0).getArrivalAirport().getFs(), equalTo("HMO"));
        assertThat(result.getBody().containsKey("returning"), equalTo(true));
        assertThat(result.getBody().get("returning").get(0).getAirline(), notNullValue());
        assertThat(result.getBody().get("returning").get(0).getAirline().getFs(), equalTo("LCT"));
        assertThat(result.getBody().get("returning").get(0).getDepartureAirport(), notNullValue());
        assertThat(result.getBody().get("returning").get(0).getDepartureAirport().getFs(), equalTo("HMO"));
        assertThat(result.getBody().get("returning").get(0).getArrivalAirport(), notNullValue());
        assertThat(result.getBody().get("returning").get(0).getArrivalAirport().getFs(), equalTo("CUU"));
        verify(scheduledFlightService);
    }

    @Test
    public void testGetScheduledFlights() throws Exception {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("CUU").to("HMO")
                .departureYear("2018").departureMonth("12").departureDay("01").build();
        List<ScheduledFlight> scheduledFlights = new ArrayList<>();
        ScheduledFlight scheduledFlight = ScheduledFlight.builder().flightNumber("826").carrierFsCode("LCT").departureAirportFsCode("CUU").arrivalAirportFsCode("HMO")
                .stops(0).departureTime("2018-12-01T16:30:00.000").arrivalTime("2018-12-01T17:50:00.000").airline(Airline.builder().name("TAR Aerolineas").fs("LCT").build())
                .departureAirport(Airport.builder().name("Gen Fierro Villalobos Airport").city("Chihuahua").countryName("Mexico").fs("CUU").build())
                .arrivalAirport(Airport.builder().name("Gen Pesqueira Garcia Airport").city("Hermosillo").countryName("Mexico").fs("HMO").build()).build();
        scheduledFlights.add(scheduledFlight);
        expect(scheduledFlightService.getScheduledFlights(anyString(), anyString(), anyString(), anyString(), anyString())).andReturn(scheduledFlights);
        replay(scheduledFlightService);
        ResponseEntity<List<ScheduledFlight>> result = scheduledFlightController.getScheduledFlights(scheduleSearch);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), notNullValue());
        assertThat(result.getBody().size(), equalTo(1));
        assertThat(result.getBody().get(0).getAirline(), notNullValue());
        assertThat(result.getBody().get(0).getAirline().getFs(), equalTo("LCT"));
        assertThat(result.getBody().get(0).getDepartureAirport(), notNullValue());
        assertThat(result.getBody().get(0).getDepartureAirport().getFs(), equalTo("CUU"));
        assertThat(result.getBody().get(0).getArrivalAirport(), notNullValue());
        assertThat(result.getBody().get(0).getArrivalAirport().getFs(), equalTo("HMO"));
        verify(scheduledFlightService);
    }
}