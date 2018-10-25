package com.nearsoft.training.spring.flightsapi.seed;

import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

@RunWith(EasyMockRunner.class)
public class DatabaseSeederTest {
    @Mock(type = MockType.NICE)
    private AirportRepository airportRepository;
    @Mock(type = MockType.NICE)
    private AirlineRepository airlineRepository;
    @Mock
    private FlightsApiConfiguration flightsApiConfiguration;
    @Mock
    private ContextRefreshedEvent contextRefreshedEvent;
    @TestSubject
    private DatabaseSeeder databaseSeeder = new DatabaseSeeder(airportRepository, airlineRepository, flightsApiConfiguration);

    @Test
    public void testSeed() throws IOException {
        EasyMock.expect(flightsApiConfiguration.getApiUrl(EasyMock.anyString(), EasyMock.anyObject(HashMap.class))).andReturn("https://api.flightstats.com/flex/airlines/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7");
        EasyMock.expect(airlineRepository.saveAll(EasyMock.anyObject())).andReturn(Collections.emptyList());
        EasyMock.expect(flightsApiConfiguration.getApiUrl(EasyMock.anyString(), EasyMock.anyObject(HashMap.class))).andReturn("https://api.flightstats.com/flex/airports/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7");
        EasyMock.expect(airportRepository.saveAll(EasyMock.anyObject())).andReturn(Collections.emptyList());
        EasyMock.replay(flightsApiConfiguration);
        EasyMock.replay(airlineRepository);
        EasyMock.replay(airportRepository);
        databaseSeeder.seed(contextRefreshedEvent);
        EasyMock.verify(flightsApiConfiguration);
        EasyMock.verify(airlineRepository);
        EasyMock.verify(airportRepository);

    }
}
