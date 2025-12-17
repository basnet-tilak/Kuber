package com.kuber.controller;

import com.kuber.common.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BaseException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBaseException(BaseException ex, ServerWebExchange exchange) {
        log.error("Business exception occurred: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getHttpStatus(),
            HttpStatus.valueOf(ex.getHttpStatus()).getReasonPhrase(),
            ex.getMessage(),
            ex.getErrorCode(),
            exchange.getRequest().getPath().value()
        );
        
        return Mono.just(ResponseEntity.status(ex.getHttpStatus()).body(errorResponse));
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAuthenticationException(AuthenticationException ex, ServerWebExchange exchange) {
        log.warn("Authentication failed: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            401,
            "Unauthorized",
            ex.getMessage(),
            ex.getErrorCode(),
            exchange.getRequest().getPath().value()
        );
        
        return Mono.just(ResponseEntity.status(401).body(errorResponse));
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleResourceNotFoundException(ResourceNotFoundException ex, ServerWebExchange exchange) {
        log.warn("Resource not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            404,
            "Not Found",
            ex.getMessage(),
            ex.getErrorCode(),
            exchange.getRequest().getPath().value()
        );
        
        return Mono.just(ResponseEntity.status(404).body(errorResponse));
    }
    
    @ExceptionHandler(ValidationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationException(ValidationException ex, ServerWebExchange exchange) {
        log.warn("Validation failed: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            400,
            "Bad Request",
            ex.getMessage(),
            ex.getErrorCode(),
            exchange.getRequest().getPath().value(),
            ex.getFieldErrors()
        );
        
        return Mono.just(ResponseEntity.status(400).body(errorResponse));
    }
    
    @ExceptionHandler(RateLimitException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleRateLimitException(RateLimitException ex, ServerWebExchange exchange) {
        log.warn("Rate limit exceeded: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            429,
            "Too Many Requests",
            ex.getMessage(),
            ex.getErrorCode(),
            exchange.getRequest().getPath().value()
        );
        
        return Mono.just(ResponseEntity.status(429).body(errorResponse));
    }
    
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        log.error("Unexpected error occurred", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            500,
            "Internal Server Error",
            "An unexpected error occurred",
            "INTERNAL_ERROR",
            exchange.getRequest().getPath().value()
        );
        
        return Mono.just(ResponseEntity.status(500).body(errorResponse));
    }
}