package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.club.Club;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

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
        @NotNull(message = "Start date is required")
        @FutureOrPresent(message = "Start date must be a date in the future")
        LocalDateTime startDate,
        @NotNull(message = "End date is required")
        @FutureOrPresent(message = "End date must be a date in the future")
        LocalDateTime endDate,
        @NotNull(message = "100")
        Club organizer
) {}
