package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.repository.AirportRepository;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private ApiUtil apiUtil;
    @TestSubject
    private AirportService airportService = new AirportService(airportRepository, apiUtil);

    @Test
    public void testSaveAll() {
        expect(airportRepository.saveAll(anyObject())).andReturn(Collections.emptyList());
        replay(airportRepository);
        airportService.saveAll(anyObject());
        verify(airportRepository);
    }

    @Test
    public void testGetAllAirportsFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/airports/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7";
        expect(apiUtil.getApiUrl(anyString(), anyObject())).andReturn(apiUrl);
        replay(apiUtil);
        airportService.getAllAirportsFromApi();
        verify(apiUtil);
    }
}