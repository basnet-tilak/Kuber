package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "trade", indexes = {
    @Index(name = "idx_trade_instr_time", columnList = "instrument_id, occurred_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "instrument_id", nullable = false)
    private Instrument instrument;
    
    @ManyToOne
    @JoinColumn(name = "taker_order_id", nullable = false)
    private Order takerOrder;
    
    @ManyToOne
    @JoinColumn(name = "maker_order_id", nullable = false)
    private Order makerOrder;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal price;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal size;
    
    @ManyToOne
    @JoinColumn(name = "fee_asset_id")
    private Asset feeAsset;
    
    @Column(name = "taker_fee", nullable = false, precision = 38, scale = 18)
    private BigDecimal takerFee = BigDecimal.ZERO;
    
    @Column(name = "maker_fee", nullable = false, precision = 38, scale = 18)
    private BigDecimal makerFee = BigDecimal.ZERO;
    
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;
}
