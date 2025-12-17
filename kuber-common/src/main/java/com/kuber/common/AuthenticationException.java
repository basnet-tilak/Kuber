package com.kuber.common;

public class AuthenticationException extends BaseException {
    
    public AuthenticationException(String message) {
        super(message, "AUTH_ERROR", 401);
    }
    
    public AuthenticationException(String message, String errorCode) {
        super(message, errorCode, 401);
    }
}