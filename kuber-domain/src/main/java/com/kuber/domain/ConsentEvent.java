package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "consent_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentEvent {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "consent_type", nullable = false)
    private ConsentType consentType;
    
    @Column(nullable = false, length = 50)
    private String version;
    
    @Column(nullable = false)
    private Boolean granted;
    
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;
    
    @Column(length = 45)
    private String ip;
    
    @Column(name = "document_url", length = 1024)
    private String documentUrl;
    
    public enum ConsentType { terms_of_service, privacy, marketing, data_sharing }
}
