package com.yoti.test.validator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.test.config.CleaningConstraintProperties;
import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.CleaningRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConstraintsCleaningValidatorTest {

    ConstraintsCleaningValidator validator;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        CleaningConstraintProperties properties = new CleaningConstraintProperties()
            .setPatchCount(3)
            .setRoomSizeX(5)
            .setRoomSizeY(5)
            .setRouteLength(15);

        validator = new ConstraintsCleaningValidator(properties);
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
            Arguments.of("{ roomSize: [5, 1], coords: [1, 0], patches: [[1, 0], [2, 0], [3, 0]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [1, 5], coords: [0, 1], patches: [[0, 2], [0, 1], [0, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [0, 1], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"N\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [0, 1], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [0, 1], patches: [], instructions: \"N\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [4, 4], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[4, 4], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [1, 1], coords: [0, 0], patches: [[0, 0]], instructions: \"NNESEESWNWW\" }")
            );
    }

    static Stream<Arguments> wrongRequestData() {
        return Stream.of(
            Arguments.of("{ roomSize: [5, 5, 1], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2, 1], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0, 1], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2, 1], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3, 1]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0, 1], [2, 2, 1], [2, 3, 1]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3], [2, 0]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWWNESWENS\" }"),
            Arguments.of("{ roomSize: [], coords: [1, 2], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }"),
            Arguments.of("{ roomSize: [5, 5], coords: [], patches: [[1, 0], [2, 2], [2, 3]], instructions: \"NNESEESWNWW\" }")
        );
    }


}
