package com.kuber.service;

import com.kuber.common.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ValidationService {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+[1-9]\\d{1,14}$"
    );
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );
    
    public void validateRegistration(String email, String phoneE164, String password, String firstName, String lastName) {
        Map<String, String> errors = new HashMap<>();
        
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.put("email", "Invalid email format");
        }
        
        if (phoneE164 == null || phoneE164.trim().isEmpty()) {
            errors.put("phoneE164", "Phone number is required");
        } else if (!PHONE_PATTERN.matcher(phoneE164).matches()) {
            errors.put("phoneE164", "Invalid phone number format (E.164)");
        }
        
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Password is required");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            errors.put("password", "Password must be at least 8 characters with uppercase, lowercase, number, and special character");
        }
        
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "First name is required");
        } else if (firstName.length() > 50) {
            errors.put("firstName", "First name must be less than 50 characters");
        }
        
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "Last name is required");
        } else if (lastName.length() > 50) {
            errors.put("lastName", "Last name must be less than 50 characters");
        }
        
        if (!errors.isEmpty()) {
            log.warn("Registration validation failed: {}", errors);
            throw new ValidationException("Registration validation failed", errors);
        }
    }
    
    public void validateLogin(String email, String password) {
        Map<String, String> errors = new HashMap<>();
        
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required");
        }
        
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Password is required");
        }
        
        if (!errors.isEmpty()) {
            log.warn("Login validation failed: {}", errors);
            throw new ValidationException("Login validation failed", errors);
        }
    }
}