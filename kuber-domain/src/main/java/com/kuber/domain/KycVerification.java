package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "kyc_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycVerification {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(length = 100)
    private String provider;
    
    @Column(length = 100)
    private String reference;
    
    @Column(name = "submitted_at", nullable = false)
    private Instant submittedAt;
    
    @Column(name = "decided_at")
    private Instant decidedAt;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    public enum Level { basic, standard, enhanced }
    public enum Status { pending, approved, rejected, expired }
}
