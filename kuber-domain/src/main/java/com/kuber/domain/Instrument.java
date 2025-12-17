package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "instrument")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrument {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "base_asset_id", nullable = false)
    private Asset baseAsset;
    
    @ManyToOne
    @JoinColumn(name = "quote_asset_id", nullable = false)
    private Asset quoteAsset;
    
    @Column(nullable = false, length = 40, unique = true)
    private String symbol;
    
    @Column(name = "min_order_size", nullable = false, precision = 30, scale = 10)
    private BigDecimal minOrderSize;
    
    @Column(name = "min_notional", nullable = false, precision = 30, scale = 10)
    private BigDecimal minNotional;
    
    @Column(name = "price_precision", nullable = false)
    private Integer pricePrecision = 8;
    
    @Column(name = "size_precision", nullable = false)
    private Integer sizePrecision = 8;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
