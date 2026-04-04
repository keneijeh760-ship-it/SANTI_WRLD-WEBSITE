package com.santiwrld.backend.entities;

import com.santiwrld.backend.config.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.security.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends AuditEntity {
    @Id
    @SequenceGenerator(name = "product",
            sequenceName = "product",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product")
    @Column(unique = true, nullable = false, name = "product_id")
    private Long Id;
    private String slug;
    private String productName;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String collection;
    @Transient
    private Boolean isActive;
    private int stockQuantity;


}
