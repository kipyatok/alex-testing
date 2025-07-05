package com.samodelkin.band.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonHelper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
            .registerModule(new JavaTimeModule());

    public static String toJson(Object bean) {
        try {
            return OBJECT_MAPPER.writeValueAsString(bean);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("cannot create JSON string, cause: %s", e.toString()));
        }
    }

    public static <T> T toBeanOrThrow(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("cannot create %s, cause: %s", type, e.toString()));
        }
    }

    public static <T> T toBeanOrNull(String json, Class<T> type) {
        try {
            return toBeanOrThrow(json, type);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static <T> Optional<T> toBeanOrEmpty(String json, Class<T> type) {
        return Optional.ofNullable(toBeanOrNull(json, type));
    }

    public static String toJsonPath(@NonNull String jsonPointer) {
        return "$" + jsonPointer
                .replaceAll("/(\\d+)/", "[$1].")
                .replaceAll("/(\\d+)", "[$1]")
                .replace("/", ".");
    }

    public static Map<String, Object> convertToJsonPaths(@NonNull Map<String, Object> pointerMap) {
        return pointerMap.entrySet().stream().collect(Collectors.toMap(
                e -> toJsonPath(e.getKey()),
                v -> v));
    }

    public static String toJsonPointer(@NonNull String jsonPath) {
        return jsonPath
                .replaceAll("\\[(\\d+)].", "/$1/")
                .replaceAll("\\[(\\d+)]", "/$1")
                .replaceAll("\\$.", "/")
                .replace(".", "/");
    }

    public static Map<String, Object> convertToJsonPointers(@NonNull Map<String, Object> pathMap) {
        return pathMap.entrySet().stream().collect(Collectors.toMap(
                e -> toJsonPointer(e.getKey()),
                Map.Entry::getValue));
    }
}
