package com.nearsoft.training.spring.flightsapi.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduledFlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetScheduledFlightsOneWay() throws Exception {
        String urlTemplate = "/flights/one-way?from=CUU&to=HMO&departureYear=2018&departureMonth=12&departureDay=01";
        this.mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$", hasKey("departing")))
                .andExpect(jsonPath("$.departing", hasSize(1)))
                .andExpect(jsonPath("$.departing.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.departing.[0].airline", hasKey("id")))
                .andExpect(jsonPath("$.departing.[0].airline.id", containsString("LCT")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.departing.[0].departureAirport", hasKey("id")))
                .andExpect(jsonPath("$.departing.[0].departureAirport.id", containsString("CUU")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport", hasKey("id")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport.id", containsString("HMO")));
    }

    @Test
    public void testGetScheduledFlightsRoundTrip() throws Exception {
        String urlTemplate = "/flights/round-trip?from=CUU&to=HMO&departureYear=2018&departureMonth=12&departureDay=01&arrivalYear=2018&arrivalMonth=12&arrivalDay=01";
        this.mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$", hasKey("departing")))
                .andExpect(jsonPath("$.departing", hasSize(1)))
                .andExpect(jsonPath("$.departing.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.departing.[0].airline", hasKey("id")))
                .andExpect(jsonPath("$.departing.[0].airline.id", containsString("LCT")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.departing.[0].departureAirport", hasKey("id")))
                .andExpect(jsonPath("$.departing.[0].departureAirport.id", containsString("CUU")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport", hasKey("id")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport.id", containsString("HMO")))
                .andExpect(jsonPath("$", hasKey("returning")))
                .andExpect(jsonPath("$.returning", hasSize(1)))
                .andExpect(jsonPath("$.returning.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.returning.[0].airline", hasKey("id")))
                .andExpect(jsonPath("$.returning.[0].airline.id", containsString("LCT")))
                .andExpect(jsonPath("$.returning.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.returning.[0].departureAirport", hasKey("id")))
                .andExpect(jsonPath("$.returning.[0].departureAirport.id", containsString("HMO")))
                .andExpect(jsonPath("$.returning.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.returning.[0].arrivalAirport", hasKey("id")))
                .andExpect(jsonPath("$.returning.[0].arrivalAirport.id", containsString("CUU")));
    }

    @Test
    public void testGetScheduledFlights() throws Exception {
        String urlTemplate = "/flights?from=CUU&to=HMO&departureYear=2018&departureMonth=12&departureDay=01";
        this.mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.[0].airline", hasKey("id")))
                .andExpect(jsonPath("$.[0].airline.id", containsString("LCT")))
                .andExpect(jsonPath("$.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.[0].departureAirport", hasKey("id")))
                .andExpect(jsonPath("$.[0].departureAirport.id", containsString("CUU")))
                .andExpect(jsonPath("$.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.[0].arrivalAirport", hasKey("id")))
                .andExpect(jsonPath("$.[0].arrivalAirport.id", containsString("HMO")));
    }
}