package com.kuber.controller;

import com.kuber.service.AuthService;
import com.kuber.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@Valid @RequestBody RegisterRequest request, ServerWebExchange exchange) {
        String ipAddress = getClientIpAddress(exchange);
        return Mono.fromCallable(() -> authService.register(request, ipAddress))
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
            .doOnSuccess(response -> log.info("Registration successful for email: {}", request.email()))
            .doOnError(error -> log.error("Registration failed for email: {}", request.email(), error));
    }
    
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return Mono.fromCallable(() -> authService.login(request))
            .map(ResponseEntity::ok)
            .doOnSuccess(response -> log.info("Login successful for email: {}", request.email()))
            .doOnError(error -> log.warn("Login failed for email: {}", request.email()));
    }
    
    @PostMapping("/refresh")
    public Mono<ResponseEntity<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return Mono.fromCallable(() -> authService.refreshToken(request))
            .map(ResponseEntity::ok)
            .doOnSuccess(response -> log.debug("Token refresh successful"))
            .doOnError(error -> log.warn("Token refresh failed", error));
    }
    
    @PostMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(@RequestHeader("Authorization") String token) {
        return Mono.fromRunnable(() -> authService.logout(token))
            .then(Mono.just(ResponseEntity.noContent().<Void>build()))
            .doOnSuccess(response -> log.info("Logout successful"))
            .doOnError(error -> log.warn("Logout failed", error));
    }
    
    private String getClientIpAddress(ServerWebExchange exchange) {
        String xForwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = exchange.getRequest().getHeaders().getFirst("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return exchange.getRequest().getRemoteAddress() != null 
            ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() 
            : "unknown";
    }
}