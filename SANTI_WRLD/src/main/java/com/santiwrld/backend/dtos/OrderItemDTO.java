package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    @NotBlank
    private String productName;
    @NotNull
    private BigDecimal price;
    @NotNull
    @Min(1)
    private int quantity;
}
