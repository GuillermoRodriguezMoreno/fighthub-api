package com.fighthub.fighthubapi.fight;

import org.springframework.stereotype.Service;

@Service
public class FightMapper {

    public Fight toFight(FightRequest request) {
        return Fight.builder()
                .fightOrder(request.fightOrder())
                .isTitleFight(request.isTitleFight())
                .isClosed(request.isClosed())
                .weight(request.weight())
                .rounds(request.rounds())
                .minutesPerRound(request.minutesPerRound())
                .blueCornerFighter(request.blueCornerFighter())
                .redCornerFighter(request.redCornerFighter())
                .event(request.event())
                .category(request.category())
                .style(request.style())
                .build();
    }

    public FightResponse toFightResponse(Fight fight) {
        return FightResponse.builder()
                .id(fight.getId())
                .fightOrder(fight.getFightOrder())
                .isTitleFight(fight.isTitleFight())
                .isClosed(fight.isClosed())
                .weight(fight.getWeight())
                .rounds(fight.getRounds())
                .minutesPerRound(fight.getMinutesPerRound())
                .blueCornerFighter(fight.getBlueCornerFighter())
                .redCornerFighter(fight.getRedCornerFighter())
                .event(fight.getEvent())
                .category(fight.getCategory())
                .style(fight.getStyle())
                .build();
    }
}
