package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
import org.easymock.*;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class AirportControllerTest extends EasyMockSupport {
    private List<Airport> airports;
    private String name;
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    @Mock
    private AirportService airportService;
    @TestSubject
    private AirportController airportController = new AirportController(airportService);

    public AirportControllerTest(List<Airport> airports, String name) {
        this.airports = airports;
        this.name = name;
    }

    @Parameterized.Parameters(name = "{index}: airports = {0}; name = {1}")
    public static Collection<Object[]> data() {
        List<Airport> airports = new ArrayList<>();
        airports.add(Airport.builder().fs("00M").name("Thigpen").city("Bay Springs").countryName("United States").build());
        return Arrays.asList(new Object[][]{
                { airports, "Thigpen" }
        });
    }

    @Test
    public void testGetAirports() {
        expect(airportService.findAll()).andReturn(airports);
        replay(airportService);
        ResponseEntity<List<Airport>> result = airportController.getAirports();
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), CoreMatchers.notNullValue());
        assertThat(result.getBody().size(), equalTo(1));
        assertThat(result.getBody().get(0).getName(), equalTo(name));
    }
}