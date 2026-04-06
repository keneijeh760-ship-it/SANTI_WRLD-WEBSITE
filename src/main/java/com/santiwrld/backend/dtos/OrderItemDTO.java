package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private BigDecimal price;
    @NotNull
    @Min(1)
    private int quantity;
}
