package com.kuber.service;

// DTOs are in same package
import com.kuber.domain.UserAccount;
import com.kuber.domain.UserProfile;
import com.kuber.domain.AppSession;
import com.kuber.security.AuthPassword;
import com.kuber.repository.UserAccountRepository;
import com.kuber.repository.UserProfileRepository;
import com.kuber.repository.AppSessionRepository;
import com.kuber.repository.AuthPasswordRepository;
import com.kuber.common.BusinessException;
import com.kuber.common.AuthenticationException;
import com.kuber.security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final AppSessionRepository appSessionRepository;
    private final AuthPasswordRepository authPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    private final ValidationService validationService;
    private final RateLimitService rateLimitService;
    private final JwtService jwtService;
    
    public AuthResponse register(RegisterRequest request, String ipAddress) {
        log.info("Attempting to register user with email: {}", request.email());
        
        rateLimitService.checkRegistrationRateLimit(ipAddress);
        
        validationService.validateRegistration(
            request.email(), 
            request.phoneE164(), 
            request.password(), 
            request.firstName(), 
            request.lastName()
        );
        
        rateLimitService.recordRegistrationAttempt(ipAddress);
        
        if (userAccountRepository.existsByEmail(request.email())) {
            log.warn("Registration failed - email already exists: {}", request.email());
            throw new BusinessException("Email already exists", "EMAIL_EXISTS");
        }
        
        String userId = UUID.randomUUID().toString();
        Instant now = Instant.now();
        
        UserAccount user = new UserAccount();
        user.setId(userId);
        user.setEmail(request.email());
        user.setPhoneE164(request.phoneE164());
        user.setStatus(UserAccount.Status.active);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setIsPep(false);
        user.setIsSanctioned(false);
        userAccountRepository.save(user);
        log.debug("Created user account with ID: {}", userId);
        
        UserProfile profile = new UserProfile();
        profile.setUserId(userId);
        profile.setUser(user);
        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setDob(java.time.LocalDate.of(1990, 1, 1)); // Default DOB - should be collected separately
        profile.setUpdatedAt(now);
        userProfileRepository.save(profile);
        
        AuthPassword authPassword = new AuthPassword();
        authPassword.setId(UUID.randomUUID().toString());
        authPassword.setUser(user);
        authPassword.setPasswordHash(passwordEncoder.encode(request.password()));
        authPassword.setAlgorithm(AuthPassword.Algorithm.argon2id);
        authPassword.setIsActive(true);
        authPassword.setCreatedAt(now);
        authPasswordRepository.save(authPassword);
        
        log.info("User registration completed successfully for email: {}", request.email());
        return createAuthResponse(user);
    }
    
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.email());
        
        rateLimitService.checkLoginRateLimit(request.email());
        rateLimitService.recordLoginAttempt(request.email());
        
        validationService.validateLogin(request.email(), request.password());
        
        UserAccount user = userAccountRepository.findByEmail(request.email())
            .orElseThrow(() -> {
                log.warn("Login failed - user not found: {}", request.email());
                return new AuthenticationException("Invalid credentials", "INVALID_CREDENTIALS");
            });
        
        AuthPassword authPassword = authPasswordRepository.findByUserAndIsActiveTrue(user)
            .orElseThrow(() -> {
                log.warn("Login failed - no active password for user: {}", request.email());
                return new AuthenticationException("Invalid credentials", "INVALID_CREDENTIALS");
            });
        
        if (!passwordEncoder.matches(request.password(), authPassword.getPasswordHash())) {
            log.warn("Login failed - invalid password for user: {}", request.email());
            throw new AuthenticationException("Invalid credentials", "INVALID_CREDENTIALS");
        }
        
        rateLimitService.clearLoginAttempts(request.email());
        log.info("Login successful for user: {}", request.email());
        return createAuthResponse(user);
    }
    
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.debug("Refreshing token for request");
        
        String userId = jwtService.extractUserId(request.refreshToken());
        UserAccount user = userAccountRepository.findById(userId)
            .orElseThrow(() -> {
                log.warn("Token refresh failed - user not found: {}", userId);
                return new AuthenticationException("Invalid token", "INVALID_TOKEN");
            });
        
        log.info("Token refreshed successfully for user: {}", userId);
        return createAuthResponse(user);
    }
    
    public void logout(String token) {
        log.debug("Processing logout request");
        
        String sessionId = jwtService.extractSessionId(token.replace("Bearer ", ""));
        appSessionRepository.findById(sessionId)
            .ifPresentOrElse(
                session -> {
                    session.setRevokedAt(Instant.now());
                    appSessionRepository.save(session);
                    log.info("Session revoked successfully: {}", sessionId);
                },
                () -> log.warn("Logout attempted for non-existent session: {}", sessionId)
            );
    }
    
    private AuthResponse createAuthResponse(UserAccount user) {
        log.debug("Creating auth response for user: {}", user.getId());
        
        Instant now = Instant.now();
        Instant expiresAt = now.plus(24, ChronoUnit.HOURS);
        
        AppSession session = new AppSession();
        session.setId(UUID.randomUUID().toString());
        session.setUser(user);
        session.setCreatedAt(now);
        session.setExpiresAt(expiresAt);
        session.setMfaLevel(AppSession.MfaLevel.none);
        appSessionRepository.save(session);
        
        String accessToken = jwtService.generateAccessToken(user.getId(), session.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), session.getId());
        
        return new AuthResponse(
            user.getId(),
            user.getEmail(),
            accessToken,
            refreshToken,
            expiresAt,
            user.getEmailVerifiedAt() != null,
            user.getPhoneVerifiedAt() != null
        );
    }
}