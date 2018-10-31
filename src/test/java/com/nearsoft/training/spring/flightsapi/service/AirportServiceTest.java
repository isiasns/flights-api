package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(EasyMockRunner.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private RestTemplate restTemplate;
    @TestSubject
    private AirportService airportService = new AirportService(airportRepository);

    @Test
    public void testGetAllAirportsFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/airports/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7";
        String response = "{\"airports\":[{\"fs\":\"00M\",\"faa\":\"00M\",\"name\":\"Thigpen\",\"city\":\"Bay Springs\",\"stateCode\":\"MS\",\"countryCode\":\"US\",\"countryName\":\"United States\",\"regionName\":\"North America\",\"timeZoneRegionName\":\"America/Chicago\",\"localTime\":\"2018-10-30T21:30:39.71\",\"utcOffsetHours\":-5.0,\"latitude\":31.987639,\"longitude\":-89.245056,\"elevationFeet\":351,\"classification\":5,\"active\":true,\"weatherUrl\":\"https://api.flightstats.com/flex/weather/rest/v1/json/all/00M?codeType=fs\",\"delayIndexUrl\":\"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/00M?codeType=fs\"},{\"fs\":\"00N\",\"faa\":\"00N\",\"name\":\"Bucks\",\"city\":\"Bridgeton\",\"stateCode\":\"NJ\",\"countryCode\":\"US\",\"countryName\":\"United States\",\"regionName\":\"North America\",\"timeZoneRegionName\":\"America/New_York\",\"localTime\":\"2018-10-30T22:30:39.71\",\"utcOffsetHours\":-4.0,\"latitude\":39.531694,\"longitude\":-75.201861,\"elevationFeet\":105,\"classification\":5,\"active\":true,\"weatherUrl\":\"https://api.flightstats.com/flex/weather/rest/v1/json/all/00N?codeType=fs\",\"delayIndexUrl\":\"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/00N?codeType=fs\"}]}";
        expect(restTemplate.getForObject(apiUrl, String.class)).andReturn(response);
        replay(restTemplate);
        assertThat(airportService.getAllAirportsFromApi(apiUrl), hasSize(2));
        verify(restTemplate);
    }
}