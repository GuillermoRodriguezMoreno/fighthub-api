package com.fighthub.fighthubapi.fight;

import org.springframework.stereotype.Service;
import com.fighthub.fighthubapi.club.Club;
import java.util.Optional;

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
                .blueCornerFighterId(fight.getBlueCornerFighter().getId())
                .blueCornerFighterName(fight.getBlueCornerFighter().getUser().getFullName())
                .blueCornerFighterClub(
                        Optional.ofNullable(fight.getBlueCornerFighter().getClub())
                                .map(Club::getName)
                                .orElse("No club")
                )                .redCornerFighterId(fight.getRedCornerFighter().getId())
                .redCornerFighterName(fight.getRedCornerFighter().getUser().getFullName())
                .redCornerFighterClub(
                        Optional.ofNullable(fight.getRedCornerFighter().getClub())
                                .map(Club::getName)
                                .orElse("No club")
                )
                .eventId(fight.getEvent().getId())
                .eventName(fight.getEvent().getName())
                .category(fight.getCategory().getName())
                .style(fight.getStyle().getName())
                .build();
    }
}
