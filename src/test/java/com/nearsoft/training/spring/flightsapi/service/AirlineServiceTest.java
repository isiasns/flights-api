package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
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
public class AirlineServiceTest {
    @Mock
    private AirlineRepository airlineRepository;
    @Mock
    private RestTemplate restTemplate;
    @TestSubject
    private AirlineService airlineService = new AirlineService(airlineRepository);


    @Test
    public void testGetAllAirlinesFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/airlines/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7";
        String response = "{\"airlines\":[{\"fs\":\"0B\",\"iata\":\"0B\",\"icao\":\"BMS\",\"name\":\"Blue Air\",\"active\":true},{\"fs\":\"0G\",\"iata\":\"G6\",\"icao\":\"GHT\",\"name\":\"Ghadames Air\",\"active\":true}]}";
        expect(restTemplate.getForObject(apiUrl, String.class)).andReturn(response);
        replay(restTemplate);
        assertThat(airlineService.getAllAirlinesFromApi(apiUrl), hasSize(2));
        verify(restTemplate);
        reset(restTemplate);
    }
}