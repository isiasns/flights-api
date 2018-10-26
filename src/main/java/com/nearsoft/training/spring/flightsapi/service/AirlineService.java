package com.nearsoft.training.spring.flightsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.training.spring.flightsapi.model.Airline;
import com.nearsoft.training.spring.flightsapi.model.Airlines;
import com.nearsoft.training.spring.flightsapi.repository.AirlineRepository;
import com.nearsoft.training.spring.flightsapi.util.ApiUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirlineService {
    private AirlineRepository airlineRepository;
    private ApiUtil apiUtil;

    public AirlineService(AirlineRepository airlineRepository, ApiUtil apiUtil) {
        this.airlineRepository = airlineRepository;
        this.apiUtil = apiUtil;
    }

    public List<Airline> saveAll(List<Airline> airlines) {
        return this.airlineRepository.saveAll(airlines);
    }

    public List<Airline> getAllAirlinesFromApi() throws IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("active", "");
        String response = new RestTemplate().getForObject(apiUtil.getApiUrl("airlines", params), String.class);
        Airlines airlines = new ObjectMapper().readValue(response, Airlines.class);
        return Arrays.asList(airlines.getAirlines());
    }

    public List<Airline> findByFsIn(List<String> fs) {
        return this.airlineRepository.findByFsIn(fs);
    }
}
