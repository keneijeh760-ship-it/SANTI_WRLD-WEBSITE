package com.santiwrld.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @SequenceGenerator(name = "order_item",
            sequenceName = "order_item",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_itm")
    @Column(unique = true, nullable = false, name = "order_item_id")
    private Long Id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private Product product;
    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;
    private BigDecimal price;
    @Column(nullable = false, name = "order_quantity")
    private int quantity;


}
