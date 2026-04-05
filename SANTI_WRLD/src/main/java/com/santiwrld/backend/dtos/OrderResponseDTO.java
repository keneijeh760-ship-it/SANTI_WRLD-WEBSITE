package com.santiwrld.backend.dtos;



import com.santiwrld.backend.entities.OrderStatus;
import com.santiwrld.backend.entities.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    @NotBlank
    private Long Id;
    @NotBlank
    private String orderReference;
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private BigDecimal totalPrice;
    @NotBlank
    private String displayTotalPrice;
    @NotBlank
    @ToString.Exclude
    private OrderStatus status;
    @NotBlank
    @ToString.Exclude
    private PaymentStatus paymentStatus;
    @NotBlank
    private Instant createdAt;
    @NotBlank
    private List<OrderItemDTO> items;
}
