package com.nearsoft.training.spring.flightsapi.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSearch {
    private String from;
    private String to;
    private String departureYear;
    private String departureMonth;
    private String departureDay;
    private String arrivalYear;
    private String arrivalMonth;
    private String arrivalDay;
}
