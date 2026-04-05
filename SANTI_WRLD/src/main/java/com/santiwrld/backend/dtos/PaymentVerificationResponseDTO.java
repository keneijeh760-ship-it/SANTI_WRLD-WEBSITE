package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentVerificationResponseDTO {
    @NotBlank
    private boolean verified;
    @NotBlank
    private String reference;
    @NotBlank
    private BigDecimal amount;

}
