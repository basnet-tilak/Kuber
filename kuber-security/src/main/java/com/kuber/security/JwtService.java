package com.kuber.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
    
    private final SecretKey secretKey;
    
    public JwtService(@Value("${security.jwt.secret:defaultSecretKeyThatShouldBeChangedInProduction}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateAccessToken(String userId, String sessionId) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(userId)
            .claim("sessionId", sessionId)
            .claim("type", "access")
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
            .signWith(secretKey)
            .compact();
    }
    
    public String generateRefreshToken(String userId, String sessionId) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(userId)
            .claim("sessionId", sessionId)
            .claim("type", "refresh")
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
            .signWith(secretKey)
            .compact();
    }
    
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }
    
    public String extractSessionId(String token) {
        return extractClaims(token).get("sessionId", String.class);
    }
    
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            log.debug("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}