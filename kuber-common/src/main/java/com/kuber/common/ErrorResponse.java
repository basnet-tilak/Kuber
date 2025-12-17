package com.kuber.common;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String errorCode,
    String path,
    Map<String, String> details
) {
    public ErrorResponse(int status, String error, String message, String errorCode, String path) {
        this(Instant.now(), status, error, message, errorCode, path, null);
    }
    
    public ErrorResponse(int status, String error, String message, String errorCode, String path, Map<String, String> details) {
        this(Instant.now(), status, error, message, errorCode, path, details);
    }
}