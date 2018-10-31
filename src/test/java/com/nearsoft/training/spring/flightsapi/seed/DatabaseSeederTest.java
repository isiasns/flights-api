package com.nearsoft.training.spring.flightsapi.seed;

import com.nearsoft.training.spring.flightsapi.service.AirlineService;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.Collections;

import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class DatabaseSeederTest {
    @Mock
    private AirportService airportService;
    @Mock
    private AirlineService airlineService;
    @Mock
    private ApiUtil apiUtil;
    @Mock
    private ContextRefreshedEvent contextRefreshedEvent;
    @TestSubject
    private DatabaseSeeder databaseSeeder = new DatabaseSeeder(airportService, airlineService, apiUtil);

    @Test
    public void testSeed() throws IOException {
        String airportsApiUrl = "https://api.flightstats.com/flex/airports/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7";
        String airlinesApiUrl = "https://api.flightstats.com/flex/airlines/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7";
        expect(apiUtil.getApiUrl(anyString(), anyObject())).andReturn(airlinesApiUrl);
        expect(airlineService.getAllAirlinesFromApi(anyString())).andReturn(Collections.emptyList());
        expect(airlineService.saveAll(anyObject())).andReturn(Collections.emptyList());
        expect(apiUtil.getApiUrl(anyString(), anyObject())).andReturn(airportsApiUrl);
        expect(airportService.getAllAirportsFromApi(anyString())).andReturn(Collections.emptyList());
        expect(airportService.saveAll(anyObject())).andReturn(Collections.emptyList());
        replay(apiUtil, airlineService, airportService);
        databaseSeeder.seed(contextRefreshedEvent);
        verify(apiUtil, airlineService, airportService);
        reset(apiUtil, airlineService, airportService);
    }
}
