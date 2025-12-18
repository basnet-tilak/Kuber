package com.kuber.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "audit_log", indexes = {
    @Index(name = "idx_audit_actor", columnList = "actor_user_id, occurred_at"),
    @Index(name = "idx_audit_entity", columnList = "entity_type, entity_id, occurred_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "actor_user_id")
    private UserAccount actorUser;
    
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private UserAccount targetUser;
    
    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;
    
    @Column(name = "entity_id", length = 36)
    private String entityId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;
    
    @Column(columnDefinition = "json")
    private String details;
    
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;
    
    public enum Action { create, update, delete, approve, reject, lock, unlock }
}
