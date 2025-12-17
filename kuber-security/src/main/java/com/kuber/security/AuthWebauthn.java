package com.kuber.security;

import com.kuber.domain.UserAccount;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "auth_webauthn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthWebauthn {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Column(name = "credential_id", nullable = false, unique = true, length = 255)
    private byte[] credentialId;
    
    @Column(name = "public_key", nullable = false, length = 4096)
    private byte[] publicKey;
    
    @Column(name = "attestation_format", length = 50)
    private String attestationFormat;
    
    @Column(name = "sign_count", nullable = false)
    private Long signCount = 0L;
    
    @Column(name = "device_label", length = 255)
    private String deviceLabel;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "revoked_at")
    private Instant revokedAt;
}