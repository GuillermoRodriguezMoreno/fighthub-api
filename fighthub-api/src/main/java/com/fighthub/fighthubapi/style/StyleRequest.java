package com.fighthub.fighthubapi.style;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StyleRequest(
        Long id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String name
) {
}
