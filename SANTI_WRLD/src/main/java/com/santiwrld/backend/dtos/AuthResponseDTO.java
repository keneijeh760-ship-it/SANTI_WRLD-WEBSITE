package com.santiwrld.backend.dtos;

import com.santiwrld.backend.entities.UserRole;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    @ToString.Exclude
    private UserRole role;
    @Valid
    private String email;
    @Valid
    private String password;
    @Valid
    private String firstName;
    @Valid
    private String lastName;

}
