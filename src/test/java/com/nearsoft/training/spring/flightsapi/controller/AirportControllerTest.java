package com.nearsoft.training.spring.flightsapi.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAirports() throws Exception {
        this.mockMvc.perform(get("/airports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$", hasSize(greaterThan(10000))));
    }
}