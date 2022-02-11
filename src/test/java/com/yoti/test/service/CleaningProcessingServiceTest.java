package com.yoti.test.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.test.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CleaningProcessingServiceTest {

    @InjectMocks
    CleaningProcessingService cleaningProcessingService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    @ParameterizedTest(name = "Correct request data test: {0}")
    @MethodSource("testData")
    void successProcessTest(String requestJson, String responseJson) throws JsonProcessingException {
        CleaningCondition request = objectMapper.readValue(requestJson, CleaningCondition.class);
        CleaningResult expectedResult = objectMapper.readValue(responseJson, CleaningResult.class);

        CleaningResult result = cleaningProcessingService.process(request);

        assertEquals(result, expectedResult);
    }


    static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of("{ roomSize: {x: 5, y: 1}, startPos: {x: 1, y: 0}, patches: [{x: 1, y: 1}], route: [] }",
                "{ currentPos: {x: 1, y: 0}, patchCount: 0}"),
            Arguments.of("{ roomSize: {x: 5, y: 5}, startPos: {x: 1, y: 2}, patches: [{x: 1, y: 1}], route: [\"SOUTH\"] }",
                "{ currentPos: {x: 1, y: 1}, patchCount: 1}"),
            Arguments.of("{ roomSize: {x: 1, y: 1}, startPos: {x: 0, y: 0}, patches: [{x: 0, y: 0}], route: [] }",
                "{ currentPos: {x: 0, y: 0}, patchCount: 1}"),
            Arguments.of("{ roomSize: {x: 1, y: 1}, startPos: {x: 0, y: 0}, patches: [{x: 0, y: 0}], route: [\"NORTH\"] }",
                "{ currentPos: {x: 0, y: 0}, patchCount: 1}"),
            Arguments.of("{ roomSize: {x: 1, y: 1}, startPos: {x: 0, y: 0}, patches: [{x: 0, y: 0}, {x: 0, y: 0}], route: [\"NORTH\"] }",
                "{ currentPos: {x: 0, y: 0}, patchCount: 1}"),
            Arguments.of("{ roomSize: {x: 1, y: 1}, startPos: {x: 0, y: 0}, patches: [{x: 0, y: 0}, {x: 0, y: 0}], route: [\"NORTH\", \"SOUTH\", \"EAST\", \"WEST\"] }",
                "{ currentPos: {x: 0, y: 0}, patchCount: 1}"),
            Arguments.of("{ roomSize: {x: 3, y: 3}, startPos: {x: 1, y: 1}, patches: [{x: 1, y: 1}, {x: 1, y: 1}], route: [\"NORTH\", \"SOUTH\", \"EAST\", \"WEST\"] }",
                "{ currentPos: {x: 1, y: 1}, patchCount: 1}"),
            Arguments.of("{ roomSize: {x: 5, y: 5}, startPos: {x: 1, y: 2}, patches: [{x: 1, y: 0}, {x: 2, y: 2}, {x: 2, y: 3}], route: [\"NORTH\", \"NORTH\", \"EAST\", \"SOUTH\", \"EAST\", \"EAST\", \"SOUTH\", \"WEST\", \"NORTH\", \"WEST\", \"WEST\"] }",
                "{ currentPos: {x: 1, y: 3}, patchCount: 1}")
        );
    }
}
