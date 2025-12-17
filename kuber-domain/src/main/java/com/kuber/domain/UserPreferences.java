package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "user_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    
    @Id
    @Column(name = "user_id", length = 36)
    private String userId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserAccount user;
    
    @ManyToOne
    @JoinColumn(name = "display_currency_asset_id", nullable = false)
    private Asset displayCurrencyAsset;
    
    @Column(name = "price_decimals", nullable = false)
    private Integer priceDecimals = 2;
    
    @Column(name = "size_decimals", nullable = false)
    private Integer sizeDecimals = 8;
    
    @Column(length = 20)
    private String locale;
    
    @Column(length = 50)
    private String timezone;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
