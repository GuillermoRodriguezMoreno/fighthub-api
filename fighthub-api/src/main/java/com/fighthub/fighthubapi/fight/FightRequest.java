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
        boolean isKo,
        boolean isDraw,
        double weight,
        int rounds,
        int minutesPerRound,
        Long likes,
        FighterProfile blueCornerFighter,
        FighterProfile redCornerFighter,
        FighterProfile winner,
        @NotNull(message = "Event is required")
        Event event,
        Category category,
        Style style
) {}
