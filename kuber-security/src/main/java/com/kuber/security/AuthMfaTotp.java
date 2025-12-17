package com.kuber.security;

import com.kuber.domain.UserAccount;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "auth_mfa_totp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMfaTotp {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Column(length = 255)
    private String label;
    
    @Column(name = "secret_encrypted", nullable = false, columnDefinition = "BLOB")
    private byte[] secretEncrypted;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "revoked_at")
    private Instant revokedAt;
}