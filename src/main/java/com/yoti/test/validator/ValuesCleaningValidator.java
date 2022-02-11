package com.yoti.test.validator;

import com.yoti.test.config.CleaningConstraintProperties;
import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.CleaningRequest;
import com.yoti.test.model.Direction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;

@Component
@RequiredArgsConstructor
public class ValuesCleaningValidator implements CleaningValidator {

    private final CleaningConstraintProperties properties;

    @Override
    public void validate(CleaningRequest request) throws ValidateException {
        Map<String, String> errors = new HashMap<>();

        if(request.getRoomSize()[0] < 1 || request.getRoomSize()[0] > properties.getRoomSizeX()) {
            errors.put("roomSize", "X size must be between 1 and " + properties.getRoomSizeX());
        }
        if(request.getRoomSize()[1] < 1 || request.getRoomSize()[1] > properties.getRoomSizeY()) {
            errors.put("roomSize", "Y size must be between 1 and " + properties.getRoomSizeY());
        }

        if (!errors.isEmpty()) {
            throw new ValidateException(errors, "Request validation error");
        }

        if(request.getCoords()[0] < 0 || request.getCoords()[0] >= request.getRoomSize()[0]) {
            errors.put("coords", "X point must be between 0 and " + max(0, request.getRoomSize()[0] - 1));
        }
        if(request.getCoords()[1] < 0 || request.getCoords()[1] >= request.getRoomSize()[1]) {
            errors.put("coords", "Y point must be between 0 and " + max(0, request.getRoomSize()[1] - 1));
        }

        String[] route = request.getInstructions().split("");
        for(String direction: route) {
            if (!Direction.isValue(direction)) {
                errors.put("instructions", "values must be among N,S,E,W");
                break;
            }
        }

        for(int [] patch: request.getPatches()) {
            if (patch[0] < 0 || patch[0] >= request.getRoomSize()[0]) {
                errors.put("patches", "X point must be between 0 and " + max(0, request.getRoomSize()[0] - 1));
                break;
            }
            if (patch[1] < 0 || patch[1] >= request.getRoomSize()[1]) {
                errors.put("patches", "Y point must be between 0 and " + max(0, request.getRoomSize()[1] - 1));
                break;
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidateException(errors, "Request validation error");
        }
    }
}
