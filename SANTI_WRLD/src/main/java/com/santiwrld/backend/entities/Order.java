package com.santiwrld.backend.entities;

import com.santiwrld.backend.config.audit.AuditEntity;
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
public class Order extends AuditEntity {
    @Id
    @SequenceGenerator(name = "order",
            sequenceName = "order",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order")
    @Column(unique = true, nullable = false, name = "order_id")
    private Long Id;
    @Column(unique = true, nullable = false, name = "order_reference")
    private String orderReference;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_order_id", nullable = false)
    private User user;
    @Column(nullable = false, unique = false, name = "customer_name")
    private String customerName;
    @Column(nullable = false, unique = true, name = "customer_email")
    private String customerEmail;
    @Column(nullable = false, unique = false, name = "delivery_addres")
    private String deliveryAddress;
    @Column(nullable = false, name = "customer_phone")
    private String customerPhone;
    private String city;
    private String state;
    @Transient
    private BigDecimal totalPrice;
    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String paymentReference;
    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;







}
