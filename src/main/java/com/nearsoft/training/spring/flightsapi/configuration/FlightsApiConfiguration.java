package com.nearsoft.training.spring.flightsapi.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    public String getApiUrl(String api, Map<String, String> parameters){
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            params.append("/");
            params.append(parameter.getKey());
            if (!parameter.getValue().isEmpty()) {
                params.append("/");
                params.append(parameter.getValue());
            }
        }
        return uri + "/" + api + "/" + protocol + "/" + version + "/" + format + params.toString() + "?"
                + "appId=" + id + "&" + "appKey=" + key;
    }
}
