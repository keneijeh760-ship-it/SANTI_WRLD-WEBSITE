package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {
    @NotBlank
    private String productName;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private int quantity;
}
