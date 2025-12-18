package com.kuber.common;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "country")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    
    @Id
    @Column(length = 2)
    private String code;
    
    @Column(nullable = false, length = 255)
    private String name;
}