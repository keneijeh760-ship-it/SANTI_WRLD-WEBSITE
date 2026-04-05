package com.santiwrld.backend.dtos;

import com.santiwrld.backend.entities.Product;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
    @Valid
    private String slug;
    @Valid
    private int quantity;
}
