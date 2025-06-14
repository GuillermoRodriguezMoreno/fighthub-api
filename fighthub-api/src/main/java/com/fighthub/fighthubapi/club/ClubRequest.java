package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import jakarta.validation.constraints.*;

public record ClubRequest(
        Long id,
        @NotNull(message = "Name is required")
        @NotEmpty(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Address is required")
        @NotEmpty(message = "Address is required")
        @NotBlank(message = "Address is required")
        String address,
        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        @NotNull(message = "Description is required")
        @NotEmpty(message = "Description is required")
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Phone is required")
        @NotEmpty(message = "Phone is required")
        @NotBlank(message = "Phone is required")
        @Size(min = 9, message = "Phone number must be at least 9 characters")
        String phone,
        @NotNull(message = "owner is required")
        FighterProfile owner
) {}
