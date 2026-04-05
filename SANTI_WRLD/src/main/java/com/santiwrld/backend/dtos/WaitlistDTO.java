package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WaitlistDTO {
    @Valid
    private String email;
}
