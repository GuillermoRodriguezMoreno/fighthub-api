package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.club.Club;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record EventRequest(
        Long id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String name,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String description,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String address,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be a date in the past")
        LocalDate startDate,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be a date in the past")
        LocalDate endDate,
        @NotNull(message = "100")
        Club organizer
) {}
