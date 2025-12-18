package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "address_book", uniqueConstraints = {
    @UniqueConstraint(name = "uq_addr_user_asset", columnNames = {"user_id", "asset_id", "address"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @Column(length = 100)
    private String label;
    
    @Column(nullable = false, length = 255)
    private String address;
    
    @Column(nullable = false)
    private Boolean whitelist = true;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
