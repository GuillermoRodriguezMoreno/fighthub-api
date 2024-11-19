package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.role.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UserRequest(
        Long id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String firstname,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String lastname,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String username,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be a date in the past")
        LocalDate dateOfBirth,
        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String password,
        boolean isAccountLocked,
        boolean isAccountEnabled,
        List<Role> roles
) {}
