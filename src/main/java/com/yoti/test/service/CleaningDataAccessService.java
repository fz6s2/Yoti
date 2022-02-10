package com.yoti.test.service;

import com.yoti.test.database.entity.CleaningConditionEntity;
import com.yoti.test.database.entity.CleaningResultEntity;
import com.yoti.test.database.repository.CleaningConditionRepository;
import com.yoti.test.mapper.CleaningDataAccessMapper;
import com.yoti.test.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CleaningDataAccessService {
    private final CleaningConditionRepository conditionRepository;
    private final CleaningDataAccessMapper mapper;

    @Transactional
    public void save(RequestCleaning requestCleaning, ResponseCleaning responseCleaning) {
        CleaningConditionEntity cleaningConditionEntity = mapper.toEntity(requestCleaning);
        CleaningResultEntity cleaningResultEntity = mapper.toEntity(responseCleaning);
        cleaningConditionEntity.setCleaningResult(cleaningResultEntity);
        cleaningResultEntity.setCleaningCondition(cleaningConditionEntity);

        conditionRepository.save(cleaningConditionEntity);
    }

    public Page<CommonResponseCleaning> getCleanings(Pageable pageable) {
        Page<CommonResponseCleaning> cleaningEntities = conditionRepository.findAll(pageable)
            .map(mapper::toCommonResponse);

        return cleaningEntities;
    }
}
