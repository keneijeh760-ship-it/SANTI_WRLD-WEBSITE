package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequestDTO {
    @Valid
    private String firstName;
    @Valid
    private String lastName;
    @Valid
    private String email;
    @Valid
    private String phoneNumber;
    @Valid
    private String address;
    @Valid
    private String city;
    @Valid
    private String state;
    @Valid
    private List<CartItemDTO> items;


}
