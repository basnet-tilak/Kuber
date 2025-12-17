package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @Column(nullable = false, length = 20, unique = true)
    private String symbol;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false)
    private Integer decimals = 8;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Kind kind = Kind.crypto;
    
    public enum Kind { crypto, fiat, token }
}
