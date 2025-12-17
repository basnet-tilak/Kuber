package com.kuber.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    
    private final SecretKey secretKey;
    
    public JwtService(@Value("${jwt.secret:defaultSecretKeyThatShouldBeChangedInProduction}") String secret) {
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
    
    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}