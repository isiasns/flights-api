package com.nearsoft.training.spring.flightsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
    @Id
    private String fs;
    private String faa;
    private String name;
    private String city;
    private String stateCode;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String timeZoneRegionName;
    private String localTime;
    private String utcOffsetHours;
    private Long latitude;
    private Long longitude;
    private Integer elevationFeet;
    private Integer classification;
    private Boolean active;
    private String weatherUrl;
    private String delayIndexUrl;
}
