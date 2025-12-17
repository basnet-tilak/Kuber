package com.kuber.service;

import com.kuber.common.RateLimitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateLimitService {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final int LOGIN_ATTEMPTS_LIMIT = 5;
    private static final int REGISTRATION_ATTEMPTS_LIMIT = 3;
    private static final Duration RATE_LIMIT_WINDOW = Duration.ofMinutes(15);
    
    public void checkLoginRateLimit(String email) {
        String key = "login_attempts:" + email;
        checkRateLimit(key, LOGIN_ATTEMPTS_LIMIT, "Too many login attempts");
    }
    
    public void checkRegistrationRateLimit(String ipAddress) {
        String key = "registration_attempts:" + ipAddress;
        checkRateLimit(key, REGISTRATION_ATTEMPTS_LIMIT, "Too many registration attempts");
    }
    
    public void recordLoginAttempt(String email) {
        String key = "login_attempts:" + email;
        recordAttempt(key);
    }
    
    public void recordRegistrationAttempt(String ipAddress) {
        String key = "registration_attempts:" + ipAddress;
        recordAttempt(key);
    }
    
    public void clearLoginAttempts(String email) {
        String key = "login_attempts:" + email;
        redisTemplate.delete(key);
        log.debug("Cleared login attempts for: {}", email);
    }
    
    private void checkRateLimit(String key, int limit, String message) {
        Integer attempts = (Integer) redisTemplate.opsForValue().get(key);
        if (attempts != null && attempts >= limit) {
            Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            log.warn("Rate limit exceeded for key: {}, attempts: {}", key, attempts);
            throw new RateLimitException(message, ttl != null ? ttl : RATE_LIMIT_WINDOW.toSeconds());
        }
    }
    
    private void recordAttempt(String key) {
        Integer attempts = (Integer) redisTemplate.opsForValue().get(key);
        if (attempts == null) {
            redisTemplate.opsForValue().set(key, 1, RATE_LIMIT_WINDOW);
        } else {
            redisTemplate.opsForValue().increment(key);
        }
        log.debug("Recorded attempt for key: {}", key);
    }
}