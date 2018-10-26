package com.nearsoft.training.spring.flightsapi.util;

import com.nearsoft.training.spring.flightsapi.configuration.FlightsApiConfiguration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApiUtil {
    private FlightsApiConfiguration flightsApiConfiguration;

    public ApiUtil(FlightsApiConfiguration flightsApiConfiguration) {
        this.flightsApiConfiguration = flightsApiConfiguration;
    }

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
        return flightsApiConfiguration.getUri() + "/" + api + "/" + flightsApiConfiguration.getProtocol() + "/" + flightsApiConfiguration.getVersion() + "/" + flightsApiConfiguration.getFormat() + required.toString() + "?"
                + "appId=" + flightsApiConfiguration.getId() + "&" + "appKey=" + flightsApiConfiguration.getKey();
    }

    public String getApiUrl(String api, Map<String, String> requiredParams, Map<String, String> optionalParams) {
        String apiUrl = getApiUrl(api, requiredParams);
        StringBuilder optional = new StringBuilder();
        if (optionalParams != null) {
            for (Map.Entry<String, String> parameter : optionalParams.entrySet()) {
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