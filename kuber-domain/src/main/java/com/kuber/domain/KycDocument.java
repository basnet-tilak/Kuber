package com.kuber.domain;

import com.kuber.common.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "kyc_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycDocument {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    
    @ManyToOne
    @JoinColumn(name = "country_code")
    private Country country;
    
    @Column(name = "number_hash", length = 255)
    private String numberHash;
    
    @Column(name = "issued_date")
    private LocalDate issuedDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Column(name = "storage_url", length = 1024)
    private String storageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.pending;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    public enum Type { passport, id_card, driver_license, proof_of_address }
    public enum VerificationStatus { pending, approved, rejected }
}
