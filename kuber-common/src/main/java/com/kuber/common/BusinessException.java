package com.kuber.common;

public class BusinessException extends BaseException {
    
    public BusinessException(String message, String errorCode) {
        super(message, errorCode, 400);
    }
    
    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, 400, cause);
    }
}