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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduledFlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetScheduledFlightsOneWay() throws Exception {
        String urlTemplate = "/flights/one-way?from=CUU&to=HMO&departureYear=2018&departureMonth=11&departureDay=01";
        this.mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$", hasKey("departing")))
                .andExpect(jsonPath("$.departing", hasSize(1)))
                .andExpect(jsonPath("$.departing.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.departing.[0].airline", hasKey("fs")))
                .andExpect(jsonPath("$.departing.[0].airline.fs", containsString("LCT")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.departing.[0].departureAirport", hasKey("fs")))
                .andExpect(jsonPath("$.departing.[0].departureAirport.fs", containsString("CUU")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport", hasKey("fs")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport.fs", containsString("HMO")));
    }

    @Test
    public void testGetScheduledFlightsRoundTrip() throws Exception {
        String urlTemplate = "/flights/round-trip?from=CUU&to=HMO&departureYear=2018&departureMonth=11&departureDay=01&arrivalYear=2018&arrivalMonth=12&arrivalDay=01";
        this.mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$", hasKey("departing")))
                .andExpect(jsonPath("$.departing", hasSize(1)))
                .andExpect(jsonPath("$.departing.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.departing.[0].airline", hasKey("fs")))
                .andExpect(jsonPath("$.departing.[0].airline.fs", containsString("LCT")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.departing.[0].departureAirport", hasKey("fs")))
                .andExpect(jsonPath("$.departing.[0].departureAirport.fs", containsString("CUU")))
                .andExpect(jsonPath("$.departing.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport", hasKey("fs")))
                .andExpect(jsonPath("$.departing.[0].arrivalAirport.fs", containsString("HMO")))
                .andExpect(jsonPath("$", hasKey("returning")))
                .andExpect(jsonPath("$.returning", hasSize(1)))
                .andExpect(jsonPath("$.returning.[0]", hasKey("airline")))
                .andExpect(jsonPath("$.returning.[0].airline", hasKey("fs")))
                .andExpect(jsonPath("$.returning.[0].airline.fs", containsString("LCT")))
                .andExpect(jsonPath("$.returning.[0]", hasKey("departureAirport")))
                .andExpect(jsonPath("$.returning.[0].departureAirport", hasKey("fs")))
                .andExpect(jsonPath("$.returning.[0].departureAirport.fs", containsString("HMO")))
                .andExpect(jsonPath("$.returning.[0]", hasKey("arrivalAirport")))
                .andExpect(jsonPath("$.returning.[0].arrivalAirport", hasKey("fs")))
                .andExpect(jsonPath("$.returning.[0].arrivalAirport.fs", containsString("CUU")));
    }
}