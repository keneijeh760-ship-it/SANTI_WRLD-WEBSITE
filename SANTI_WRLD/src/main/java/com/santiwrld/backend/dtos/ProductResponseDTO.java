package com.santiwrld.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String slug;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private String displayPrice;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String collection;


}
