package com.yoti.test.service;

import com.yoti.test.database.entity.CleaningConditionEntity;
import com.yoti.test.database.entity.CleaningResultEntity;
import com.yoti.test.database.repository.CleaningConditionRepository;
import com.yoti.test.mapper.CleaningDataAccessMapper;
import com.yoti.test.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CleaningDataAccessServiceTest {

    @Mock
    CleaningConditionRepository cleaningConditionRepository;
    @Mock
    CleaningDataAccessMapper mapper;
    @InjectMocks
    CleaningDataAccessService cleaningDataAccessService;

    @Test
    void successStartCleaningTest() {
        CleaningRequest cleaningRequest = getRequestCleaning();
        CleaningResponse cleaningResponse = getResponseCleaning();
        CleaningConditionEntity cleaningConditionEntity = getCleaningConditionEntity(100L, 200L);
        CleaningResultEntity cleaningResultEntity = cleaningConditionEntity.getCleaningResult();

        when(mapper.toEntity(cleaningRequest)).thenReturn(cleaningConditionEntity);
        when(mapper.toEntity(cleaningResponse)).thenReturn(cleaningResultEntity);

        cleaningDataAccessService.save(cleaningRequest, cleaningResponse);
        verify(mapper, times(1)).toEntity(cleaningRequest);
        verify(mapper, times(1)).toEntity(cleaningResponse);
        verify(cleaningConditionRepository, times(1)).save(cleaningConditionEntity);
    }

    CleaningResponse getResponseCleaning() {
        int [] coords = {0, 0};
        return new CleaningResponse(coords, 0);
    }

    CleaningRequest getRequestCleaning() {
        int [] roomSize = {5, 5};
        int [] coords = {1, 0};
        int [][] patches = {{1, 0}};
        return new CleaningRequest(roomSize, coords, patches, "N");
    }

    private CleaningConditionEntity getCleaningConditionEntity(Long conditionEntityId, Long resultEntityId) {
        CleaningConditionEntity conditionEntity = new CleaningConditionEntity()
            .setId(conditionEntityId)
            .setCreatedAt(LocalDateTime.now())
            .setData("{}");
        CleaningResultEntity resultEntity = new CleaningResultEntity()
            .setId(resultEntityId)
            .setCreatedAt(LocalDateTime.now())
            .setData("{[]}")
            .setCleaningCondition(conditionEntity);

        conditionEntity.setCleaningResult(resultEntity);

        return conditionEntity;
    }

}
