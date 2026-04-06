package com.santiwrld.backend.dtos;



import com.santiwrld.backend.entities.OrderStatus;
import com.santiwrld.backend.entities.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long id;
    @NotBlank
    private String orderReference;
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerEmail;
    @NotNull
    private BigDecimal totalPrice;
    @NotBlank
    private String displayTotalPrice;
    @NotNull
    @ToString.Exclude
    private OrderStatus status;
    @NotNull
    @ToString.Exclude
    private PaymentStatus paymentStatus;
    @NotNull
    private Instant createdAt;
    @NotNull
    private List<OrderItemDTO> items;
}
