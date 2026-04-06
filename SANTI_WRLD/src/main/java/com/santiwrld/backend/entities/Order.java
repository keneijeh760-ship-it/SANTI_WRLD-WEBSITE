package com.santiwrld.backend.entities;

import com.santiwrld.backend.config.audit.AuditEntity;
import com.santiwrld.backend.dtos.CartItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
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
    private List<CartItemDTO> cartItems;







}
