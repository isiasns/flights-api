package com.nearsoft.training.spring.flightsapi.configuration;

import lombok.Data;
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

    public String getApiUrl(String api, Map<String, String> requiredParams) {
        StringBuilder required = new StringBuilder();
        if (requiredParams != null) {
            for (Map.Entry<String, String> parameter : requiredParams.entrySet()) {
                required.append("/");
                required.append(parameter.getKey());
                if (!parameter.getValue().isEmpty()) {
                    required.append("/");
                    required.append(parameter.getValue());
                }
            }
        }
        return uri + "/" + api + "/" + protocol + "/" + version + "/" + format + required.toString() + "?"
                + "appId=" + id + "&" + "appKey=" + key;
    }

    public String getApiUrl(String api, Map<String, String> requiredParams, Map<String, String> optionalParams) {
        String apiUrl = this.getApiUrl(api, requiredParams);
        StringBuilder optional = new StringBuilder();
        if (optionalParams != null) {
            for (Map.Entry<String, String> parameter : requiredParams.entrySet()) {
                if (!parameter.getValue().isEmpty()) {
                    optional.append("&");
                    optional.append(parameter.getKey());
                    optional.append("=");
                    optional.append(parameter.getValue());
                }
            }
        }
        return apiUrl + optional.toString();
    }
}
