package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.role.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record UserRequest(
        Long id,
        @NotNull(message = "First name is required")
        @NotEmpty(message = "First name is required")
        @NotBlank(message = "First name is required")
        String firstname,
        @NotNull(message = "Last name is required")
        @NotEmpty(message = "Last name is required")
        @NotBlank(message = "Last name is required")
        String lastname,
        @NotNull(message = "username is required")
        @NotEmpty(message = "username is required")
        @NotBlank(message = "username is required")
        String username,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be a date in the past")
        LocalDate dateOfBirth,
        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        @NotNull(message = "Password is required")
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        boolean isAccountLocked,
        boolean isAccountEnabled,
        Set<Role> roles
) {}
