package com.fighthub.fighthubapi.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ResgistrationRequest {

    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstname;
    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastname;
    @NotEmpty(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;
    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
