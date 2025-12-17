package com.kuber.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        
        return Mono.fromCallable(() -> {
            if (jwtService.isTokenValid(token)) {
                String userId = jwtService.extractUserId(token);
                String sessionId = jwtService.extractSessionId(token);
                
                log.debug("Authenticated user: {} with session: {}", userId, sessionId);
                
                return (Authentication) new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
            return null;
        })
        .doOnError(ex -> log.warn("JWT authentication failed", ex))
        .onErrorReturn(null);
    }
}