package com.osvalr.minesweeper.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

public class FieldConverter {
    @Nullable
    public static String toJson(@Nonnull Field field) {
        requireNonNull(field);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(field);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Nullable
    public static <T> T fromJson(@Nonnull Class<T> clz, @Nonnull String value) {
        requireNonNull(clz);
        requireNonNull(value);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(value, clz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
