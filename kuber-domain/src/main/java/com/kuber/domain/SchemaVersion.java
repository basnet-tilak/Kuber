package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "schema_version")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaVersion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "version_label", nullable = false, length = 100)
    private String versionLabel;
    
    @Column(name = "applied_at", nullable = false)
    private Instant appliedAt;
    
    @Column(length = 1000)
    private String notes;
}
