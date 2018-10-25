package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

@RunWith(EasyMockRunner.class)
public class AirlineServiceTest {
    @Mock
    private AirlineRepository airlineRepository;
    @Mock
    private FlightsApiConfiguration flightsApiConfiguration;
    @TestSubject
    private AirlineService airlineService = new AirlineService(airlineRepository, flightsApiConfiguration);

    @Test
    public void testSaveAll() {
        EasyMock.expect(airlineRepository.saveAll(EasyMock.anyObject())).andReturn(Collections.emptyList());
        EasyMock.replay(airlineRepository);
        airlineService.saveAll(EasyMock.anyObject());
        EasyMock.verify(airlineRepository);
    }

    @Test
    public void testGetAllAirlinesFromApi() throws IOException {
        EasyMock.expect(flightsApiConfiguration.getApiUrl(EasyMock.anyString(), EasyMock.anyObject())).andReturn("https://api.flightstats.com/flex/airlines/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7");
        EasyMock.replay(flightsApiConfiguration);
        airlineService.getAllAirlinesFromApi();
        EasyMock.verify(flightsApiConfiguration);
    }
}