package com.kuber.security;

import com.kuber.domain.UserAccount;
import com.kuber.domain.AppSession;
import com.kuber.common.Device;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "login_event", indexes = {
    @Index(name = "idx_login_user_time", columnList = "user_id, occurred_at"),
    @Index(name = "idx_login_ip", columnList = "ip")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginEvent {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    
    @ManyToOne
    @JoinColumn(name = "session_id")
    private AppSession session;
    
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;
    
    @Column(nullable = false, length = 45)
    private String ip;
    
    @Column(name = "ip_country_code", length = 2)
    private String ipCountryCode;
    
    @Column(name = "ip_city", length = 100)
    private String ipCity;
    
    @Column(name = "ip_latitude")
    private Double ipLatitude;
    
    @Column(name = "ip_longitude")
    private Double ipLongitude;
    
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    
    @Column(name = "is_success", nullable = false)
    private Boolean isSuccess;
    
    @Column(name = "failure_reason", length = 255)
    private String failureReason;
    
    @Column(name = "risk_score", precision = 6, scale = 2)
    private BigDecimal riskScore = BigDecimal.ZERO;
    
    @Column(columnDefinition = "json")
    private String metadata;
}