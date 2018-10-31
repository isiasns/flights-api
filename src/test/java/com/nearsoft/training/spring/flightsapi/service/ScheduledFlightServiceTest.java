package com.nearsoft.training.spring.flightsapi.service;

import com.nearsoft.training.spring.flightsapi.model.ScheduledFlight;
import com.nearsoft.training.spring.flightsapi.search.ScheduleSearch;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(EasyMockRunner.class)
public class ScheduledFlightServiceTest {
    @Mock
    private ApiUtil apiUtil;
    @Mock
    private AirlineService airlineService;
    @Mock
    private AirportService airportService;
    @Mock
    private RestTemplate restTemplate;
    @TestSubject
    private ScheduledFlightService scheduledFlightService = new ScheduledFlightService(apiUtil, airlineService, airportService);

    @Test
    public void testGetScheduledFlightsFromApi() throws IOException {
        String apiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/CUU/to/HMO/departing/2018/11/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        String response = "{\"scheduledFlights\":[{\"carrierFsCode\":\"LCT\",\"flightNumber\":\"828\",\"departureAirportFsCode\":\"CUU\",\"arrivalAirportFsCode\":\"HMO\",\"stops\":0,\"departureTime\":\"2018-11-01T20:15:00.000\",\"arrivalTime\":\"2018-11-01T21:35:00.000\",\"flightEquipmentIataCode\":\"ERJ\",\"isCodeshare\":false,\"isWetlease\":false,\"serviceType\":\"J\",\"serviceClasses\":[\"Y\"],\"trafficRestrictions\":[],\"codeshares\":[],\"referenceCode\":\"664-4347737--\"}]}";
        expect(restTemplate.getForObject(apiUrl, String.class)).andReturn(response);
        replay(restTemplate);
        assertThat(scheduledFlightService.getScheduledFlightsFromApi(apiUrl), hasSize(1));
        verify(restTemplate);
    }

    @Test
    public void testGetOneWayScheduledFlights() throws IOException {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("CUU").to("HMO").departureYear("2018").departureMonth("11")
                .departureDay("01").build();
        String apiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/CUU/to/HMO/departing/2018/11/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        String response = "{\"scheduledFlights\":[{\"carrierFsCode\":\"LCT\",\"flightNumber\":\"828\",\"departureAirportFsCode\":\"CUU\",\"arrivalAirportFsCode\":\"HMO\",\"stops\":0,\"departureTime\":\"2018-11-01T20:15:00.000\",\"arrivalTime\":\"2018-11-01T21:35:00.000\",\"flightEquipmentIataCode\":\"ERJ\",\"isCodeshare\":false,\"isWetlease\":false,\"serviceType\":\"J\",\"serviceClasses\":[\"Y\"],\"trafficRestrictions\":[],\"codeshares\":[],\"referenceCode\":\"664-4347737--\"}]}";
        expect(apiUtil.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(apiUrl);
        expect(restTemplate.getForObject(apiUrl, String.class)).andReturn(response);
        replay(restTemplate, apiUtil);
        assertThat(scheduledFlightService.getOneWayScheduledFlights(scheduleSearch), IsMapContaining.hasKey("departing"));
        verify(restTemplate, apiUtil);
    }

    @Test
    public void testGetRoundTripScheduledFlights() throws IOException {
        ScheduleSearch scheduleSearch = ScheduleSearch.builder().from("CUU").to("HMO").departureYear("2018").departureMonth("11")
                .departureDay("01").build();
        String departingApiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/CUU/to/HMO/departing/2018/11/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        String returningApiUrl = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/HMO/to/CUU/departing/2018/12/01?appId=94f1a83f&appKey=2e4539c28bc1bc0ac82b19b286a3f7d7&codeType=FS";
        String departingResponse = "{\"scheduledFlights\":[{\"carrierFsCode\":\"LCT\",\"flightNumber\":\"828\",\"departureAirportFsCode\":\"CUU\",\"arrivalAirportFsCode\":\"HMO\",\"stops\":0,\"departureTime\":\"2018-11-01T20:15:00.000\",\"arrivalTime\":\"2018-11-01T21:35:00.000\",\"flightEquipmentIataCode\":\"ERJ\",\"isCodeshare\":false,\"isWetlease\":false,\"serviceType\":\"J\",\"serviceClasses\":[\"Y\"],\"trafficRestrictions\":[],\"codeshares\":[],\"referenceCode\":\"664-4347737--\"}]}";
        String returningResponse = "{\"scheduledFlights\":[{\"carrierFsCode\":\"LCT\",\"flightNumber\":\"827\",\"departureAirportFsCode\":\"HMO\",\"arrivalAirportFsCode\":\"CUU\",\"stops\":0,\"departureTime\":\"2018-12-01T18:15:00.000\",\"arrivalTime\":\"2018-12-01T19:35:00.000\",\"flightEquipmentIataCode\":\"ERJ\",\"isCodeshare\":false,\"isWetlease\":false,\"serviceType\":\"J\",\"serviceClasses\":[\"Y\"],\"trafficRestrictions\":[],\"codeshares\":[],\"referenceCode\":\"664-4347931--\"}]}";
        expect(apiUtil.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(departingApiUrl);
        expect(restTemplate.getForObject(departingApiUrl, String.class)).andReturn(departingResponse);
        expect(apiUtil.getApiUrl(anyString(), anyObject(), anyObject())).andReturn(returningApiUrl);
        expect(restTemplate.getForObject(returningApiUrl, String.class)).andReturn(returningResponse);
        expect(airlineService.findByFsIn(anyObject())).andReturn(Collections.emptyList()).times(2);
        expect(airportService.findByFsIn(anyObject())).andReturn(Collections.emptyList()).times(2);
        replay(restTemplate, apiUtil, airlineService, airportService);
        Map<String, List<ScheduledFlight>> scheduledFlights = scheduledFlightService.getRoundTripScheduledFlights(scheduleSearch);
        assertThat(scheduledFlights, IsMapContaining.hasKey("departing"));
        assertThat(scheduledFlights, IsMapContaining.hasKey("returning"));
        verify(restTemplate, apiUtil, airlineService, airportService);
    }
}