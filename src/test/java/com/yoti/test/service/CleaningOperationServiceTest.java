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
    CleaningValidator validator;
    @Mock
    CleaningOperationsMapper mapper;
    @Mock
    CleaningDataAccessService cleaningDataAccessService;
    @Mock
    CleaningProcessingService cleaningProcessingService;
    @InjectMocks
    CleaningOperationService cleaningOperationService;

    @Test
    void successCleaningOperation() {
        RequestCleaning requestCleaning = getRequestCleaning();
        CleaningCondition cleaningCondition = getCleaningCondition();
        CleaningResult cleaningResult = getCleaningResult();
        ResponseCleaning responseCleaningExpected = getResponseCleaning();

        doNothing().when(validator).validate(requestCleaning);
        when(mapper.toDto(requestCleaning)).thenReturn(cleaningCondition);
        when(cleaningProcessingService.process(cleaningCondition)).thenReturn(cleaningResult);
        when(mapper.toResponse(cleaningResult)).thenReturn(responseCleaningExpected);
        doNothing().when(cleaningDataAccessService).save(requestCleaning, responseCleaningExpected);

        ResponseCleaning responseCleaning = cleaningOperationService.startCleaning(requestCleaning);

        assertNotNull(responseCleaning);
        assertEquals(responseCleaningExpected, responseCleaning);

        verify(validator, times(1)).validate(requestCleaning);
        verify(mapper, times(1)).toDto(requestCleaning);
        verify(mapper, times(1)).toResponse(cleaningResult);
        verify(cleaningDataAccessService, times(1)).save(requestCleaning, responseCleaningExpected);

    }

    CleaningResult getCleaningResult() {
        return new CleaningResult(new PairXY(0, 0), 0);
    }

    ResponseCleaning getResponseCleaning() {
        int [] coords = {0, 0};
        return new ResponseCleaning(coords, 0);
    }

    CleaningCondition getCleaningCondition() {
        List<PairXY> patches = new ArrayList<>();
        patches.add(new PairXY(1,0));

        List<Direction> route = new ArrayList<>();
        route.add(Direction.NORTH);

        return CleaningCondition.builder()
            .roomSize(new PairXY(5,5))
            .startPos(new PairXY(1,0))
            .patches(patches)
            .route(route)
            .build();
    }

    RequestCleaning getRequestCleaning() {
        int [] roomSize = {5, 5};
        int [] coords = {1, 0};
        int [][] patches = {{1, 0}};
        //return new RequestCleaning(roomSize, coords, patches, "N");
        return null;
    }

}
