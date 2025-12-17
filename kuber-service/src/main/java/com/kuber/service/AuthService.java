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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final AppSessionRepository appSessionRepository;
    private final AuthPasswordRepository authPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AuthResponse register(RegisterRequest request) {
        if (userAccountRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
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
        
        return createAuthResponse(user);
    }
    
    public AuthResponse login(LoginRequest request) {
        UserAccount user = userAccountRepository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        AuthPassword authPassword = authPasswordRepository.findByUserAndIsActiveTrue(user)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(request.password(), authPassword.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        return createAuthResponse(user);
    }
    
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String userId = jwtService.extractUserId(request.refreshToken());
        UserAccount user = userAccountRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Invalid token"));
        
        return createAuthResponse(user);
    }
    
    public void logout(String token) {
        String sessionId = jwtService.extractSessionId(token.replace("Bearer ", ""));
        appSessionRepository.findById(sessionId)
            .ifPresent(session -> {
                session.setRevokedAt(Instant.now());
                appSessionRepository.save(session);
            });
    }
    
    private AuthResponse createAuthResponse(UserAccount user) {
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