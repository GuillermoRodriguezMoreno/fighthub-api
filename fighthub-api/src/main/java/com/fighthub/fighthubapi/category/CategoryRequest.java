package com.fighthub.fighthubapi.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        Long id,
        @NotNull(message = "Name is required")
        @NotEmpty(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name
) {
}
