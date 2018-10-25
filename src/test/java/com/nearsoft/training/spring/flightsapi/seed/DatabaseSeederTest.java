package com.nearsoft.training.spring.flightsapi.seed;

import com.nearsoft.training.spring.flightsapi.service.AirlineService;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.Collections;

@RunWith(EasyMockRunner.class)
public class DatabaseSeederTest {
    @Mock
    private AirportService airportService;
    @Mock
    private AirlineService airlineService;
    @Mock
    private ContextRefreshedEvent contextRefreshedEvent;
    @TestSubject
    private DatabaseSeeder databaseSeeder = new DatabaseSeeder(airportService, airlineService);

    @Test
    public void testSeed() throws IOException {
        EasyMock.expect(airlineService.getAllAirlinesFromApi()).andReturn(Collections.emptyList());
        EasyMock.expect(airlineService.saveAll(EasyMock.anyObject())).andReturn(Collections.emptyList());
        EasyMock.expect(airportService.getAllAirportsFromApi()).andReturn(Collections.emptyList());
        EasyMock.expect(airportService.saveAll(EasyMock.anyObject())).andReturn(Collections.emptyList());
        EasyMock.replay(airlineService);
        EasyMock.replay(airportService);
        databaseSeeder.seed(contextRefreshedEvent);
        EasyMock.verify(airlineService);
        EasyMock.verify(airportService);

    }
}
