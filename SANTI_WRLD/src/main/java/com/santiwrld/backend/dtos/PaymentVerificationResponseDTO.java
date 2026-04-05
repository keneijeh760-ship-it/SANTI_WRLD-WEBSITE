package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentVerificationResponseDTO {
    @Valid
    private boolean verified;
    @Valid
    private String reference;
    @Valid
    private long amount;

}
