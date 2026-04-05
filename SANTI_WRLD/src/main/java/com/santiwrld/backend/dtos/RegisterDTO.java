package com.santiwrld.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {
    @Valid
    private String firstName;
    @Valid
    private String lastName;
    @Valid
    private String email;
    @Valid
    @Length(min = 8)
    private String password;
}
