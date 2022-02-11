package com.yoti.test.validator;

import com.yoti.test.config.CleaningConstraintProperties;
import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.CleaningRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConstraintsCleaningValidator implements CleaningValidator {

    private final CleaningConstraintProperties properties;

    @Override
    public void validate(CleaningRequest request) throws ValidateException {
        Map<String, String> errors = new HashMap<>();
        if(request.getRoomSize().length != 2) {
            errors.put("roomSize", "array length must be 2");
        }
        if(request.getCoords().length != 2) {
            errors.put("coords", "array length must be 2");
        }
        if(request.getInstructions().length() > properties.getRouteLength()) {
            errors.put("instructions", "length cannot be more then " + properties.getRouteLength());
        }
        if(request.getPatches().length > properties.getPatchCount()) {
            errors.put("patches", "array length cannot be more then " + properties.getPatchCount());
        } else {
            for(int [] patch: request.getPatches()) {
                if (patch.length != 2) {
                    errors.put("patches", "all patch length inside must be 2");
                    break;
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidateException(errors, "Request validation error");
        }
    }
}
