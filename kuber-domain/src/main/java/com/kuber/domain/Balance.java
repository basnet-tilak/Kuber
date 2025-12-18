package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "balance", uniqueConstraints = {
    @UniqueConstraint(name = "uq_balance_acc_asset", columnNames = {"account_id", "asset_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    
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
    private BigDecimal total = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal available = BigDecimal.ZERO;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
