package com.nearsoft.training.spring.flightsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledFlight {
    private String carrierFsCode;
    private String flightNumber;
    private String departureAirportFsCode;
    private String arrivalAirportFsCode;
    private Integer stops;
    private String departureTerminal;
    private String arrivalTerminal;
    private String departureTime;
    private String arrivalTime;
    private String flightEquipmentIataCode;
    private Boolean isCodeshare;
    private Boolean isWetlease;
    private String serviceType;
    private String[] serviceClasses;
    private String[] trafficRestrictions;
    private Codeshare[] codeshares;
    private String referenceCode;
}
