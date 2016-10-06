package br.com.iworks.movie.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonHelper {

    private ObjectMapper mapper;

    public JsonHelper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(final String message, final Class<T> clazz) {
        try {
            return mapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}