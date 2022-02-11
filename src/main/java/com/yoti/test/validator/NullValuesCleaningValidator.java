package com.yoti.test.validator;

import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.CleaningRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NullValuesCleaningValidator implements CleaningValidator {

    public void validate(CleaningRequest request) throws ValidateException{
        if(request == null) {
            throw new ValidateException("Request cannot be empty");
        }

        Map<String, String> errors = new HashMap<>();
        if (request.getRoomSize() == null) {
            errors.put("roomSize", "cannot be empty");
        }
        if (request.getCoords() == null) {
            errors.put("coords", "cannot be empty");
        }
        if (request.getInstructions() == null) {
            errors.put("instructions", "cannot be empty");
        }
        if (request.getPatches() == null) {
            errors.put("patches", "cannot be empty");
        }

        if(!errors.isEmpty()) {
            throw new ValidateException(errors, "Request validation error");
        }
    }
}
