package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.location.Location;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record FighterProfileRequest(
        @NotNull(message = "Weight is required")
        double weight,
        @NotNull(message = "Height is required")
        int height,
        @NotNull(message = "Gender is required")
        @NotEmpty(message = "Gender is required")
        @NotBlank(message = "Gender is required")
        String gender,
        String biography,
        Integer wins,
        Integer losses,
        Integer draws,
        Integer ko,
        Integer winsInARow,
        Location location,
        @NotNull(message = "100")
        User user,
        @NotNull(message = "Style are required")
        Set<Style> styles,
        @NotNull(message = "Category are required")
        Category category,
        Club club
) {}
