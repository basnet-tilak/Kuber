package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "risk_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskScore {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Domain domain;
    
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal score;
    
    @Column(length = 255)
    private String reason;
    
    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;
    
    public enum Domain { login, transaction, withdrawal, global }
}
