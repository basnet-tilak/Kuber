package com.kuber.common;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "device")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @Column(name = "ua_full", nullable = false, length = 1024)
    private String uaFull;
    
    @Column(name = "browser_name", length = 100)
    private String browserName;
    
    @Column(name = "browser_version", length = 50)
    private String browserVersion;
    
    @Column(name = "os_name", length = 100)
    private String osName;
    
    @Column(name = "os_version", length = 50)
    private String osVersion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    private DeviceType deviceType;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    public enum DeviceType { desktop, mobile, tablet, bot, unknown }
}