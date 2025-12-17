package com.kuber.common;

public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", 404);
    }
    
    public ResourceNotFoundException(String resourceType, String identifier) {
        super(String.format("%s not found with identifier: %s", resourceType, identifier), 
              "RESOURCE_NOT_FOUND", 404);
    }
}