package com.nearsoft.training.spring.flightsapi.seed;

import com.nearsoft.training.spring.flightsapi.service.AirlineService;
import com.nearsoft.training.spring.flightsapi.service.AirportService;
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
    private ContextRefreshedEvent contextRefreshedEvent;
    @TestSubject
    private DatabaseSeeder databaseSeeder = new DatabaseSeeder(airportService, airlineService);

    @Test
    public void testSeed() throws IOException {
        expect(airlineService.getAllAirlinesFromApi()).andReturn(Collections.emptyList());
        expect(airlineService.saveAll(anyObject())).andReturn(Collections.emptyList());
        expect(airportService.getAllAirportsFromApi()).andReturn(Collections.emptyList());
        expect(airportService.saveAll(anyObject())).andReturn(Collections.emptyList());
        replay(airlineService);
        replay(airportService);
        databaseSeeder.seed(contextRefreshedEvent);
        verify(airlineService);
        verify(airportService);

    }
}
