package com.santiwrld.backend.dtos;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {
    @Valid
    private Long id;
    @Valid
    private String slug;
    @Valid
    private String name;
    @Valid
    private String description;
    @Valid
    private Long price;
    @Valid
    private String displayPrice;
    @Valid
    private String imageUrl;
    @Valid
    private String collection;


}
