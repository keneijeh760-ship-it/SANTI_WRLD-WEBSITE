package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    @Valid
    private String username;
    @Length(min = 8)
    @Valid
    private String password;
}
