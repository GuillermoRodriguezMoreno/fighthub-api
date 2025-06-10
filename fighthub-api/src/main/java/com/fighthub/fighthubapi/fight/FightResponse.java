package com.fighthub.fighthubapi.fight;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FightResponse {
    private Long id;
    private Integer fightOrder;
    private LocalDateTime fightDate;
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
    private String categoryName;
    private Long categoryId;
    private String styleName;
    private Long styleId;
}
