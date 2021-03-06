package com.nearsoft.training.spring.flightsapi.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
    @Id
    private String fs;
    private String name;
    private String city;
    private String countryName;

    @JsonGetter("id")
    public String getFs() {
        return this.fs;
    }

    @JsonSetter("fs")
    public void setFs(String fs) {
        this.fs = fs;
    }
}
