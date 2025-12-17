package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fx_rate", 
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_fx_pair_time", columnNames = {"from_asset_id", "to_asset_id", "effective_at"})
    },
    indexes = {
        @Index(name = "idx_fx_pair_latest", columnList = "from_asset_id, to_asset_id, effective_at")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxRate {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "from_asset_id", nullable = false)
    private Asset fromAsset;
    
    @ManyToOne
    @JoinColumn(name = "to_asset_id", nullable = false)
    private Asset toAsset;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal rate;
    
    @Column(length = 100)
    private String source;
    
    @Column(name = "effective_at", nullable = false)
    private Instant effectiveAt;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
