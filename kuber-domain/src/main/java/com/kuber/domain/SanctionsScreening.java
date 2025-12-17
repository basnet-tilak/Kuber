package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "sanctions_screening")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanctionsScreening {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Column(length = 100)
    private String provider;
    
    @Column(name = "list_name", length = 100)
    private String listName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(precision = 6, scale = 2)
    private BigDecimal score;
    
    @Column(name = "screened_at", nullable = false)
    private Instant screenedAt;
    
    @Column(columnDefinition = "json")
    private String details;
    
    public enum Status { clear, match, review }
}
