package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.style.Style;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FightResponse {
    private Long id;
    private int fightOrder;
    private boolean isTitleFight;
    private boolean isClosed;
    private double weight;
    private int rounds;
    private int minutesPerRound;
    private FighterProfile blueCornerFighter;
    private FighterProfile redCornerFighter;
    private Event event;
    private Category category;
    private Style style;
}
