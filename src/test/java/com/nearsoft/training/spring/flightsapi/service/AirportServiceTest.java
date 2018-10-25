package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

@RunWith(EasyMockRunner.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private FlightsApiConfiguration flightsApiConfiguration;
    @TestSubject
    private AirportService airportService = new AirportService(airportRepository, flightsApiConfiguration);

    @Test
    public void testSaveAll() {
        EasyMock.expect(airportRepository.saveAll(EasyMock.anyObject())).andReturn(Collections.emptyList());
        EasyMock.replay(airportRepository);
        airportService.saveAll(EasyMock.anyObject());
        EasyMock.verify(airportRepository);
    }

    @Test
    public void testGetAllAirportsFromApi() throws IOException {
        EasyMock.expect(flightsApiConfiguration.getApiUrl(EasyMock.anyString(), EasyMock.anyObject())).andReturn("https://api.flightstats.com/flex/airports/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7");
        EasyMock.replay(flightsApiConfiguration);
        airportService.getAllAirportsFromApi();
        EasyMock.verify(flightsApiConfiguration);
    }
}