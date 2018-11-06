package com.nearsoft.training.spring.flightsapi.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    public static JsonNode getJsonCollection(String jsonText, String collectionName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonText);
        return node.at(collectionName);
    }
}
