package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "config_feature_flag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigFeatureFlag {
    
    @Id
    @Column(name = "`key`", length = 100)
    private String key;
    
    @Column(nullable = false)
    private Boolean enabled;
    
    @Column(name = "rollout_percentage", nullable = false)
    private Integer rolloutPercentage;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    
    @Column(columnDefinition = "json")
    private String metadata;
}
