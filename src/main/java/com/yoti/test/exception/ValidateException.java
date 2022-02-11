package com.yoti.test.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ValidateException extends RuntimeException {

    @Getter
    private Map<String, String> errors = new HashMap<>();

    public ValidateException(Map<String, String> errors, String message) {
        super(message);
        this.errors = errors;
    }

    public ValidateException(String message) {
        super(message);
    }
}
