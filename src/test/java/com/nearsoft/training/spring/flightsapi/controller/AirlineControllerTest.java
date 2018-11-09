package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.service.AirlineService;
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
public class AirlineControllerTest extends EasyMockSupport {
    private List<Airline> airlines;
    private String name;
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    @Mock
    private AirlineService airlineService;
    @TestSubject
    private AirlineController airlineController = new AirlineController(airlineService);

    public AirlineControllerTest(List<Airline> airlines, String name) {
        this.airlines = airlines;
        this.name = name;
    }

    @Parameterized.Parameters(name = "{index}: airlines = {0}; name = {1}")
    public static Collection<Object[]> data() {
        List<Airline> airlines = new ArrayList<>();
        airlines.add(Airline.builder().fs("0B").name("Blue Air").build());
        return Arrays.asList(new Object[][]{
                { airlines, "Blue Air" }
        });
    }

    @Test
    public void testGetAirlines() {
        expect(airlineService.findAll()).andReturn(airlines);
        replay(airlineService);
        ResponseEntity<List<Airline>> result = airlineController.getAirlines();
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), CoreMatchers.notNullValue());
        assertThat(result.getBody().size(), equalTo(1));
        assertThat(result.getBody().get(0).getName(), equalTo(name));
    }
}