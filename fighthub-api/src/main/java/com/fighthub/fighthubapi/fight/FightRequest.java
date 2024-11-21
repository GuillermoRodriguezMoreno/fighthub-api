package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.style.Style;
import jakarta.validation.constraints.NotNull;

public record FightRequest(
        long id,
        @NotNull(message = "100")
        int fightOrder,
        boolean isTitleFight,
        boolean isClosed,
        double weight,
        int rounds,
        int minutesPerRound,
        @NotNull(message = "Blue corner fighter is required")
        FighterProfile blueCornerFighter,
        @NotNull(message = "Red corner fighter is required")
        FighterProfile redCornerFighter,
        @NotNull(message = "Event is required")
        Event event,
        Category category,
        Style style
) {}
