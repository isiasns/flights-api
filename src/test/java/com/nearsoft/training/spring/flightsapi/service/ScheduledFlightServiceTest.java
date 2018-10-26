package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class ScheduledFlightServiceTest {
    @Mock
    private FlightsApiConfiguration flightsApiConfiguration;
    @Mock
    private AirlineService airlineService;
    @Mock
    private AirportService airportService;
    @TestSubject
    private ScheduledFlightService scheduledFlightService = new ScheduledFlightService(flightsApiConfiguration, airlineService, airportService);

    @Test
    public void testGetScheduledFlightsFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/LAX/to/JFK/departing/2018/11/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        expect(flightsApiConfiguration.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(apiUrl);
        replay(flightsApiConfiguration);
        scheduledFlightService.getScheduledFlightsFromApi("LAX", "JFK", "2018", "11", "01");
        verify(flightsApiConfiguration);
    }
}