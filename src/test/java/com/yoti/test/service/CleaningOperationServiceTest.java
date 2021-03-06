package com.yoti.test.service;

import com.yoti.test.mapper.CleaningOperationsMapper;
import com.yoti.test.model.*;
import com.yoti.test.validator.CleaningValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CleaningOperationServiceTest {

    @Mock
    CleaningValidator cleaningValidator;
    @Mock
    CleaningOperationsMapper mapper;
    @Mock
    CleaningDataAccessService cleaningDataAccessService;
    @Mock
    CleaningProcessingService cleaningProcessingService;
    @InjectMocks
    CleaningOperationService cleaningOperationService;

    @Test
    void successStartCleaningTest() {
        CleaningRequest cleaningRequest = getRequestCleaning();
        CleaningCondition cleaningCondition = getCleaningCondition();
        CleaningResult cleaningResult = getCleaningResult();
        CleaningResponse cleaningResponseExpected = getResponseCleaning();

        doNothing().when(cleaningValidator).validate(cleaningRequest);

        when(mapper.toDto(cleaningRequest)).thenReturn(cleaningCondition);
        when(cleaningProcessingService.process(cleaningCondition)).thenReturn(cleaningResult);
        when(mapper.toResponse(cleaningResult)).thenReturn(cleaningResponseExpected);
        doNothing().when(cleaningDataAccessService).save(cleaningRequest, cleaningResponseExpected);

        CleaningResponse cleaningResponse = cleaningOperationService.startCleaning(cleaningRequest);

        assertNotNull(cleaningResponse);
        assertEquals(cleaningResponseExpected, cleaningResponse);

        verify(cleaningValidator, times(3)).validate(cleaningRequest);
        verify(mapper, times(1)).toDto(cleaningRequest);
        verify(mapper, times(1)).toResponse(cleaningResult);
        verify(cleaningDataAccessService, times(1)).save(cleaningRequest, cleaningResponseExpected);

    }

    CleaningResult getCleaningResult() {
        return new CleaningResult(new PairXY(0, 0), 0);
    }

    CleaningResponse getResponseCleaning() {
        int [] coords = {0, 0};
        return new CleaningResponse(coords, 0);
    }

    CleaningCondition getCleaningCondition() {
        List<PairXY> patches = new ArrayList<>();
        patches.add(new PairXY(1,0));

        List<Direction> route = new ArrayList<>();
        route.add(Direction.NORTH);

        return new CleaningCondition()
            .setRoomSize(new PairXY(5,5))
            .setStartPos(new PairXY(1,0))
            .setPatches(patches)
            .setRoute(route);
    }

    CleaningRequest getRequestCleaning() {
        int [] roomSize = {5, 5};
        int [] coords = {1, 0};
        int [][] patches = {{1, 0}};
        return new CleaningRequest(roomSize, coords, patches, "N");
    }

}
