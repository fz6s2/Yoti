package com.yoti.test.service;

import com.yoti.test.mapper.CleaningOperationsMapper;
import com.yoti.test.model.*;
import com.yoti.test.validator.CleaningValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CleaningOperationService {

    private final CleaningValidator validator;
    private final CleaningOperationsMapper mapper;
    private final CleaningDataAccessService cleaningDataAccessService;
    private final CleaningProcessingService cleaningProcessingService;

    public CleaningResponse startCleaning(CleaningRequest cleaningRequest) {
        validator.validate(cleaningRequest);

        CleaningCondition cleaningCondition = mapper.toDto(cleaningRequest);

        CleaningResult cleaningResult = cleaningProcessingService.process(cleaningCondition);

        CleaningResponse cleaningResponse = mapper.toResponse(cleaningResult);

        cleaningDataAccessService.save(cleaningRequest, cleaningResponse);

        return cleaningResponse;
    }
}
