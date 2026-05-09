package com.example.pharmacy.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class JacksonMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private JacksonMapper() {
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(source, targetClass);
    }

    public static <S, T> List<T> mapList(Collection<S> sources, Class<T> targetClass) {
        if (sources == null) {
            return Collections.emptyList();
        }
        return sources.stream()
                .map(source -> map(source, targetClass))
                .toList();
    }
}

