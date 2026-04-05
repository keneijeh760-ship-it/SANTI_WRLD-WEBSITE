package com.santiwrld.backend.dtos;

import com.santiwrld.backend.entities.Product;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdeResponseDTO {
    @Valid
    private Long Id;
    @Valid
    private String orderReference;
    @Valid
    private String customerName;
    @Valid
    private String customerEmail;
    @Valid
    private Long totalPrice;
    @Valid
    private String displayTotalPrice;
    @Valid
    private String status;
    @Valid
    private String paymentStatus;
    @Valid
    private Instant createdAt;
    @Valid
    private List<OrderItemDTO> items;
}
