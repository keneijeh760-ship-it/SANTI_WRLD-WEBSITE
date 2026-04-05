package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDTO {
    @Valid
    private String productName;
    @Valid
    private String productDescription;
    @Valid
    private BigDecimal productPrice;
    @Valid
    private Boolean active;
    @Valid
    private int stockQuantity;
    @Valid
    private String imageUrl;
}
