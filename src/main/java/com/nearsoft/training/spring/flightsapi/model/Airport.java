package com.nearsoft.training.spring.flightsapi.model;

import com.fasterxml.jackson.annotation.JsonGetter;
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
    private String name;
    private String city;
    private String countryName;

    @JsonGetter("id")
    public String getFs(){
        return this.fs;
    }
}
