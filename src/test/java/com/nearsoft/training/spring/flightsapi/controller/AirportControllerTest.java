package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(EasyMockRunner.class)
public class AirportControllerTest {
    @Mock
    private AirportService airportService;
    @TestSubject
    private AirportController airportController = new AirportController(airportService);

    @Test
    public void testGetAirports() {
        List<Airport> airports = new ArrayList<>();
        Airport airport = Airport.builder().fs("00M").name("Thigpen").city("Bay Springs").countryName("United States").build();
        airports.add(airport);
        expect(airportService.findAll()).andReturn(airports);
        replay(airportService);
        ResponseEntity<List<Airport>> result = airportController.getAirports();
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), CoreMatchers.notNullValue());
        assertThat(result.getBody().size(), equalTo(1));
    }
}