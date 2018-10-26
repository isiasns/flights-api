package com.nearsoft.training.spring.flightsapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("flights")
public class FlightsApiConfiguration {
    private String uri;
    private String protocol;
    private String version;
    private String format;
    private String id;
    private String key;
}
