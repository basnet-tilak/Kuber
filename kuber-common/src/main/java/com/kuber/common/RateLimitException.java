package com.kuber.common;

public class RateLimitException extends BaseException {
    
    public RateLimitException(String message) {
        super(message, "RATE_LIMIT_EXCEEDED", 429);
    }
    
    public RateLimitException(String message, long retryAfterSeconds) {
        super(String.format("%s. Retry after %d seconds", message, retryAfterSeconds), "RATE_LIMIT_EXCEEDED", 429);
    }
}