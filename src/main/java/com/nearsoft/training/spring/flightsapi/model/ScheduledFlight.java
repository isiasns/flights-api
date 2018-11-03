package com.nearsoft.training.spring.flightsapi.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    private Airline airline;
    private Airport departureAirport;
    private Airport arrivalAirport;

    @JsonGetter("id")
    public String getFlightNumber(){
        return this.flightNumber;
    }

    @JsonSetter("flightNumber")
    public void setFlightNumber(String flightNumber){
        this.flightNumber = flightNumber;
    }
}
