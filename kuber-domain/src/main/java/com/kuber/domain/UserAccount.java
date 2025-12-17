package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "user_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @Column(nullable = false, length = 320)
    private String email;
    
    @Column(name = "phone_e164", length = 20)
    private String phoneE164;
    
    @Column(name = "email_verified_at")
    private Instant emailVerifiedAt;
    
    @Column(name = "phone_verified_at")
    private Instant phoneVerifiedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    
    @Column(name = "is_pep", nullable = false)
    private Boolean isPep;
    
    @Column(name = "is_sanctioned", nullable = false)
    private Boolean isSanctioned;
    
    public enum Status { active, locked, closed }
}