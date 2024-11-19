package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record FighterProfileRequest(
        @NotNull(message = "100")
        double weight,
        @NotNull(message = "100")
        int height,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String gender,
        String biography,
        @NotNull(message = "100")
        User user,
        Set<Style> styles,
        Category category,
        Club club
) {}
