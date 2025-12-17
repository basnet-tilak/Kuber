package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "pep_screening")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PepScreening {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(name = "screened_at", nullable = false)
    private Instant screenedAt;
    
    @Column(columnDefinition = "json")
    private String details;
    
    public enum Status { clear, pep, review }
}
