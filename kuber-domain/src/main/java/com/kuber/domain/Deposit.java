package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "deposit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.pending;
    
    @Column(name = "tx_ref", length = 255)
    private String txRef;
    
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;
    
    public enum Status { pending, completed, failed }
}
