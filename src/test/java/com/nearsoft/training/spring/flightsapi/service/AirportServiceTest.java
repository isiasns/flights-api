package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.model.Airport;
import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(Parameterized.class)
public class AirportServiceTest extends EasyMockSupport {
    private String apiUrl;
    private String response;
    private String firstFsCode;
    private String secondFsCode;
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private RestTemplate restTemplate;
    @TestSubject
    private AirportService airportService = new AirportService(airportRepository);

    public AirportServiceTest(String apiUrl, String response, String firstFsCode, String secondFsCode) {
        this.apiUrl = apiUrl;
        this.response = response;
        this.firstFsCode = firstFsCode;
        this.secondFsCode = secondFsCode;
    }

    @Parameterized.Parameters(name = "{index}: apiUrl = {0}; response = {1}; firstFsCode = {2}; secondFsCode = {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
                {
                        {
                                "https://api.flightstats.com/flex/airports/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7",
                                "{\"airports\":[{\"fs\":\"00M\",\"faa\":\"00M\",\"name\":\"Thigpen\",\"city\":\"Bay Springs\",\"stateCode\":\"MS\",\"countryCode\":\"US\",\"countryName\":\"United States\",\"regionName\":\"North America\",\"timeZoneRegionName\":\"America/Chicago\",\"localTime\":\"2018-10-30T21:30:39.71\",\"utcOffsetHours\":-5.0,\"latitude\":31.987639,\"longitude\":-89.245056,\"elevationFeet\":351,\"classification\":5,\"active\":true,\"weatherUrl\":\"https://api.flightstats.com/flex/weather/rest/v1/json/all/00M?codeType=fs\",\"delayIndexUrl\":\"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/00M?codeType=fs\"},{\"fs\":\"00N\",\"faa\":\"00N\",\"name\":\"Bucks\",\"city\":\"Bridgeton\",\"stateCode\":\"NJ\",\"countryCode\":\"US\",\"countryName\":\"United States\",\"regionName\":\"North America\",\"timeZoneRegionName\":\"America/New_York\",\"localTime\":\"2018-10-30T22:30:39.71\",\"utcOffsetHours\":-4.0,\"latitude\":39.531694,\"longitude\":-75.201861,\"elevationFeet\":105,\"classification\":5,\"active\":true,\"weatherUrl\":\"https://api.flightstats.com/flex/weather/rest/v1/json/all/00N?codeType=fs\",\"delayIndexUrl\":\"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/00N?codeType=fs\"}]}",
                                "00M",
                                "00N"
                        }
                }
        );
    }

    @Test
    public void testGetAllAirportsFromApi() throws IOException {
        expect(restTemplate.getForObject(apiUrl, String.class)).andReturn(response);
        replay(restTemplate);
        List<Airport> airports = airportService.getAllAirportsFromApi(apiUrl);
        assertThat(airports, hasSize(2));
        assertThat(airports.get(0).getFs(), equalTo(firstFsCode));
        assertThat(airports.get(1).getFs(), equalTo(secondFsCode));
        verify(restTemplate);
    }
}