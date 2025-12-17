package com.kuber.security;

import com.kuber.domain.UserAccount;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "auth_password")
@Data
@NoArgsConstructor
public class AuthPassword {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Algorithm algorithm;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "deactivated_at")
    private Instant deactivatedAt;
    
    public enum Algorithm { argon2id, bcrypt, scrypt }
}