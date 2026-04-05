package com.santiwrld.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;

    private String slug;

    private String name;

    private String description;
    private BigDecimal price;

    private String displayPrice;

    private String imageUrl;

    private String collection;


}
