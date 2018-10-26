package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
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
public class AirlineServiceTest {
    @Mock
    private AirlineRepository airlineRepository;
    @Mock
    private ApiUtil apiUtil;
    @TestSubject
    private AirlineService airlineService = new AirlineService(airlineRepository, apiUtil);

    @Test
    public void testSaveAll() {
        expect(airlineRepository.saveAll(anyObject())).andReturn(Collections.emptyList()).atLeastOnce();
        replay(airlineRepository);
        airlineService.saveAll(anyObject());
        verify(airlineRepository);
    }

    @Test
    public void testGetAllAirlinesFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/airlines/rest/v1/json/active?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7";
        expect(apiUtil.getApiUrl(anyString(), anyObject())).andReturn(apiUrl);
        replay(apiUtil);
        airlineService.getAllAirlinesFromApi();
        verify(apiUtil);
    }
}