package com.nearsoft.training.spring.flightsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Codeshare {
    private String carrierFsCode;
    private String flightNumber;
    private String serviceType;
    private String[] serviceClasses;
    private String[] trafficRestrictions;
    private String referenceCode;
}
