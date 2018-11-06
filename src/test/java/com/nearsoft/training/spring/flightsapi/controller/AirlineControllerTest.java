package com.nearsoft.training.spring.flightsapi.controller;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.service.AirlineService;
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
public class AirlineControllerTest {
    @Mock
    private AirlineService airlineService;
    @TestSubject
    private AirlineController airlineController = new AirlineController(airlineService);

    @Test
    public void testGetAirlines() {
        List<Airline> airlines = new ArrayList<>();
        Airline airline = Airline.builder().fs("0B").name("Blue Air").build();
        airlines.add(airline);
        expect(airlineService.findAll()).andReturn(airlines);
        replay(airlineService);
        ResponseEntity<List<Airline>> result = airlineController.getAirlines();
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), CoreMatchers.notNullValue());
        assertThat(result.getBody().size(), equalTo(1));
    }
}