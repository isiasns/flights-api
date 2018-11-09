package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
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
public class AirlineServiceTest extends EasyMockSupport {
    private String apiUrl;
    private String response;
    private String firstFsCode;
    private String secondFsCode;

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    @Mock
    private AirlineRepository airlineRepository;
    @Mock
    private RestTemplate restTemplate;
    @TestSubject
    private AirlineService airlineService = new AirlineService(airlineRepository);

    public AirlineServiceTest(String apiUrl, String response, String firstFsCode, String secondFsCode) {
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
                                "https://api.flightstats.com/flex/airlines/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7",
                                "{\"airlines\":[{\"fs\":\"0B\",\"iata\":\"0B\",\"icao\":\"BMS\",\"name\":\"Blue Air\",\"active\":true},{\"fs\":\"0G\",\"iata\":\"G6\",\"icao\":\"GHT\",\"name\":\"Ghadames Air\",\"active\":true}]}",
                                "0B",
                                "0G"
                        }
                }
        );
    }

    @Test
    public void testGetAllAirlinesFromApi() throws IOException {
        expect(restTemplate.getForObject(apiUrl, String.class)).andReturn(response);
        replay(restTemplate);
        List<Airline> airlines = airlineService.getAllAirlinesFromApi(apiUrl);
        assertThat(airlines, hasSize(2));
        assertThat(airlines.get(0).getFs(), equalTo(firstFsCode));
        assertThat(airlines.get(1).getFs(), equalTo(secondFsCode));
        verify(restTemplate);
        reset(restTemplate);
    }
}