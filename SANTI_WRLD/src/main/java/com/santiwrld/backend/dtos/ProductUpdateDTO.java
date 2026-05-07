package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {

    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Boolean active;
    private Integer stockQuantity;
    private String imageUrl;
}
