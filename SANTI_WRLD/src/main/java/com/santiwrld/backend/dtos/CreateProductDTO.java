package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductDTO {
    @Valid
    private String slug;
    @Valid
    private String name;
    @Valid
    private String description;
    @Valid
    private String imageUrl;
    @Valid
    private int price;
    @Valid
    private String collection;
}
