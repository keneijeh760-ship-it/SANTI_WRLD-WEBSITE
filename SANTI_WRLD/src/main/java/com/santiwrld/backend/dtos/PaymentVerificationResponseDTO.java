package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentVerificationResponseDTO {
    @NotNull
    private boolean verified;
    @NotBlank
    private String reference;
    @NotNull
    private BigDecimal amount;

}
