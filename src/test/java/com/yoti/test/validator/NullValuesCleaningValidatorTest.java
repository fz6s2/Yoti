package com.yoti.test.validator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.CleaningRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

public class NullValuesCleaningValidatorTest {

    NullValuesCleaningValidator validator;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        validator = new NullValuesCleaningValidator();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    @ParameterizedTest(name = "Correct request data test: {0}")
    @MethodSource("correctRequestData")
    void correctRequestDataTest(String json) throws JsonProcessingException {
        CleaningRequest request = objectMapper.readValue(json, CleaningRequest.class);

        validator.validate(request);
    }

    @ParameterizedTest(name = "Wrong request data test: {0}")
    @MethodSource("wrongRequestData")
    void wrongRequestDataTest(String json) throws JsonProcessingException {
        CleaningRequest request = objectMapper.readValue(json, CleaningRequest.class);

        assertThrows(ValidateException.class, () -> validator.validate(request));
    }

    static Stream<Arguments> correctRequestData() {
        return Stream.of(
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [], instructions: \"\" }"),
            Arguments.of("{ roomSize: [0, 0], coords: [1, 2], patches: [], instructions: \"\" }"),
            Arguments.of("{ roomSize: [1, 2], coords: [0, 0], patches: [], instructions: \"\" }")
            );
    }

    static Stream<Arguments> wrongRequestData() {
        return Stream.of(
            Arguments.of("{ coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]] }")
        );
    }


}
