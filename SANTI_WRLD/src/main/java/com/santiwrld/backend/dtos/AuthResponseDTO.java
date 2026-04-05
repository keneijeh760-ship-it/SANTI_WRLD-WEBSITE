package com.santiwrld.backend.dtos;

import com.santiwrld.backend.entities.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDTO {
    @NotBlank
    private String token;
    @ToString.Exclude
    @NotBlank
    private UserRole role;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}
