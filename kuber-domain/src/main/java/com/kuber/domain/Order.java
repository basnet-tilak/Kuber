package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders", 
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_orders_client_id", columnNames = {"account_id", "client_order_id"})
    },
    indexes = {
        @Index(name = "idx_orders_instr_status", columnList = "instrument_id, status")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @ManyToOne
    @JoinColumn(name = "instrument_id", nullable = false)
    private Instrument instrument;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Side side;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    
    @Column(precision = 38, scale = 18)
    private BigDecimal price;
    
    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal size;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "time_in_force")
    private TimeInForce timeInForce = TimeInForce.gtc;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.new_;
    
    @Column(name = "client_order_id", length = 64)
    private String clientOrderId;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    
    public enum Side { buy, sell }
    public enum Type { market, limit, stop_limit }
    public enum TimeInForce { gtc, ioc, fok }
    public enum Status { new_, partially_filled, filled, canceled, expired }
}
