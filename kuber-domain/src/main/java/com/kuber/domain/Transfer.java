package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;
    
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
    
    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amount;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(length = 255)
    private String reason;
}
