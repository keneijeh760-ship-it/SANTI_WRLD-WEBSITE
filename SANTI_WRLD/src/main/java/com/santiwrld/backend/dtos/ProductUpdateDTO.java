package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {
    @NotBlank
    private String productName;
    @NotBlank
    private String productDescription;
    @NotBlank
    private BigDecimal productPrice;
    @NotBlank
    private Boolean active;
    @NotBlank
    private Integer stockQuantity;
    @NotBlank
    private String imageUrl;
}
