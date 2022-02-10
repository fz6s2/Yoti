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

    public ResponseCleaning startCleaning(RequestCleaning requestCleaning) {
        validator.validate(requestCleaning);

        CleaningCondition cleaningCondition = mapper.toDto(requestCleaning);

        CleaningResult cleaningResult = cleaningProcessingService.process(cleaningCondition);

        ResponseCleaning responseCleaning = mapper.toResponse(cleaningResult);

        cleaningDataAccessService.save(requestCleaning, responseCleaning);

        return responseCleaning;
    }
}
