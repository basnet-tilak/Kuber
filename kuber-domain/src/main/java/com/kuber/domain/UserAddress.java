package com.kuber.domain;

import com.kuber.common.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type = Type.residential;
    
    @Column(nullable = false, length = 255)
    private String line1;
    
    @Column(length = 255)
    private String line2;
    
    @Column(nullable = false, length = 100)
    private String city;
    
    @Column(length = 100)
    private String region;
    
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    
    @ManyToOne
    @JoinColumn(name = "country_code", nullable = false)
    private Country country;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    
    public enum Type { residential, mailing, business }
}
