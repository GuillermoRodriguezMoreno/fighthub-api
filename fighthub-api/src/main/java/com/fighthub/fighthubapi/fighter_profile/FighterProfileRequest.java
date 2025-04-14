package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.location.Location;
import com.fighthub.fighthubapi.picture.Picture;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Set;

public record FighterProfileRequest(
        @NotNull(message = "First name is required")
        @NotEmpty(message = "First name is required")
        @NotBlank(message = "First name is required")
        String firstname,
        @NotNull(message = "Last name is required")
        @NotEmpty(message = "Last name is required")
        @NotBlank(message = "Last name is required")
        String lastname,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be a date in the past")
        LocalDate dateOfBirth,
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
        Integer kos,
        Integer winsInARow,
        Location location,
        User user,
        @NotNull(message = "Style are required")
        Set<Style> styles,
        @NotNull(message = "Category are required")
        Category category,
        Club club,
        Set<Picture> pictures
) {}
