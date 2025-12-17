package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "app_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppSession {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;
    
    @Column(name = "revoked_at")
    private Instant revokedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "mfa_level", nullable = false)
    private MfaLevel mfaLevel;
    
    public enum MfaLevel { none, totp, webauthn, both }
}