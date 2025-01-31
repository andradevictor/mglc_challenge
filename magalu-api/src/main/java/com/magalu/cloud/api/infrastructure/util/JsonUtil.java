package com.magalu.cloud.api.infrastructure.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static <K> K fromString(String json, Class<K> clazz) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static <K> String toJson(K object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
