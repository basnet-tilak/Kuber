package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "account", indexes = {
    @Index(name = "idx_account_user", columnList = "user_id, created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @ManyToOne
    @JoinColumn(name = "base_currency_asset_id")
    private Asset baseCurrencyAsset;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Kind kind = Kind.spot;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.active;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    public enum Kind { spot, funding }
    public enum Status { active, frozen, closed }
}
