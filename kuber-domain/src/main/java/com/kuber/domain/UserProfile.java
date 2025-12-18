package com.kuber.domain;

import com.kuber.common.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    
    @Id
    @Column(name = "user_id", length = 36)
    private String userId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserAccount user;
    
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    
    @Column(nullable = false)
    private LocalDate dob;
    
    @ManyToOne
    @JoinColumn(name = "nationality_code", referencedColumnName = "code")
    private Country nationality;
    
    @Column(name = "tax_id", length = 64)
    private String taxId;
    
    @Column(length = 100)
    private String occupation;
    
    @Column(name = "source_of_funds", length = 255)
    private String sourceOfFunds;
    
    @Column(name = "pii_encrypted", columnDefinition = "BLOB")
    private byte[] piiEncrypted;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
