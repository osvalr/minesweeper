package com.osvalr.minesweeper.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

public class JsonHelper {
    /**
     * Serializes using Jackson's ObjectMapper any object
     * @param obj instance to be serialized
     * @return String representation of obj as JSON, null in case of any error
     */
    @Nullable
    public static <T> String toJson(@Nonnull T obj) {
        requireNonNull(obj);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * De-serializes using Jackson's ObjectMapper string
     * @param clz Class of the instance that represents the string value
     * @param value JSON string version value to be de-serialized
     * @return Object instance of value, null in case of any error
     */
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
