package com.kuber.common;

import java.util.Map;

public class ValidationException extends BaseException {
    
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message, "VALIDATION_ERROR", 400);
        this.fieldErrors = fieldErrors;
    }
    
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", 400);
        this.fieldErrors = null;
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}