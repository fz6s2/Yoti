package com.yoti.test.validator;

import com.yoti.test.config.CleaningConstraintProperties;
import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.Direction;
import com.yoti.test.model.RequestCleaning;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.Math.max;

@Component
@RequiredArgsConstructor
public class CleaningValidator {
    private final CleaningConstraintProperties properties;

    public void validate(RequestCleaning request) {
        checkNull(request);
        checkConstraints(request);
        checkValues(request);
    }

    private void checkNull(RequestCleaning request) throws ValidateException {
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

    private void checkConstraints(RequestCleaning request) throws ValidateException {
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
        if(request.getInstructions().length() == 0 || request.getInstructions().length() > properties.getRouteLength()) {
            errors.put("instructions", "length must be between 1 and " + properties.getRouteLength());
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

    private void checkValues(RequestCleaning request) throws ValidateException {
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
