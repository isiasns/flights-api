package com.nearsoft.training.spring.flightsapi.util;

import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApiUtil {
    private final static String SLASH = "/";
    private final static String QUESTION = "?";
    private final static String EQUALS = "=";
    private final static String AMP = "&";
    private FlightsApiConfiguration flightsApiConfiguration;

    @Autowired
    public ApiUtil(FlightsApiConfiguration flightsApiConfiguration) {
        this.flightsApiConfiguration = flightsApiConfiguration;
    }

    public String getApiUrl(String api, Map<String, String> requiredParams) {
        StringBuilder builder = new StringBuilder();
        builder.append(flightsApiConfiguration.getUri());
        builder.append(SLASH);
        builder.append(api);
        builder.append(SLASH);
        builder.append(flightsApiConfiguration.getProtocol());
        builder.append(SLASH);
        builder.append(flightsApiConfiguration.getVersion());
        builder.append(SLASH);
        builder.append(flightsApiConfiguration.getFormat());
        if (requiredParams != null) {
            for (Map.Entry<String, String> parameter : requiredParams.entrySet()) {
                builder.append(SLASH);
                builder.append(parameter.getKey());
                if (!parameter.getValue().isEmpty()) {
                    builder.append(SLASH);
                    builder.append(parameter.getValue());
                }
            }
        }
        builder.append(QUESTION);
        builder.append("appId=");
        builder.append(flightsApiConfiguration.getId());
        builder.append(AMP);
        builder.append("appKey=");
        builder.append(flightsApiConfiguration.getKey());
        return builder.toString();
    }

    public String getApiUrl(String api, Map<String, String> requiredParams, Map<String, String> optionalParams) {
        String apiUrl = getApiUrl(api, requiredParams);
        StringBuilder builder = new StringBuilder();
        builder.append(apiUrl);
        if (optionalParams != null) {
            for (Map.Entry<String, String> parameter : optionalParams.entrySet()) {
                if (!parameter.getValue().isEmpty()) {
                    builder.append(AMP);
                    builder.append(parameter.getKey());
                    builder.append(EQUALS);
                    builder.append(parameter.getValue());
                }
            }
        }
        return builder.toString();
    }
}