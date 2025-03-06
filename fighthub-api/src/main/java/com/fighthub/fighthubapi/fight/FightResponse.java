package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.category.CategoryResponse;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.event.EventResponse;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileResponse;
import com.fighthub.fighthubapi.style.Style;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FightResponse {
    private Long id;
    private Integer fightOrder;
    private boolean isTitleFight;
    private boolean isClosed;
    private boolean isKo;
    private boolean isDraw;
    private double weight;
    private Integer rounds;
    private Integer minutesPerRound;
    private Long likes;
    private Long winnerId;
    private String winnerName;
    private Long blueCornerFighterId;
    private String blueCornerFighterName;
    private String blueCornerFighterClub;
    private Long redCornerFighterId;
    private String redCornerFighterName;
    private String redCornerFighterClub;
    private Long eventId;
    private String eventName;
    private String category;
    private String style;
}
