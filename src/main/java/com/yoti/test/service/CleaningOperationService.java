package com.yoti.test.service;

import com.yoti.test.mapper.CleaningOperationsMapper;
import com.yoti.test.model.*;
import com.yoti.test.validator.CleaningValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The {@code CleaningOperationService} class represents a service to perform a clean-up of an abstract room.
 * Clean-up consists of 3 important steps:
 * 1) Input data validation
 * 2) Robotic hoover work
 * 3) Saving clean-up result
 *
 * @see CleaningRequest
 * @see CleaningResponse
 */

@Service
@RequiredArgsConstructor
public class CleaningOperationService {

    private final CleaningOperationsMapper mapper;
    private final CleaningDataAccessService cleaningDataAccessService;
    private final CleaningProcessingService cleaningProcessingService;
    private final CleaningValidator nullValuesCleaningValidator;
    private final CleaningValidator constraintsCleaningValidator;
    private final CleaningValidator valuesCleaningValidator;

    public CleaningResponse startCleaning(CleaningRequest cleaningRequest) {
        nullValuesCleaningValidator.validate(cleaningRequest);
        constraintsCleaningValidator.validate(cleaningRequest);
        valuesCleaningValidator.validate(cleaningRequest);

        CleaningCondition cleaningCondition = mapper.toDto(cleaningRequest);

        CleaningResult cleaningResult = cleaningProcessingService.process(cleaningCondition);

        CleaningResponse cleaningResponse = mapper.toResponse(cleaningResult);

        cleaningDataAccessService.save(cleaningRequest, cleaningResponse);

        return cleaningResponse;
    }
}
