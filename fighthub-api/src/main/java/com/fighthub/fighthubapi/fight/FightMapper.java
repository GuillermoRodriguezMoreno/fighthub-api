package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.style.Style;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FightMapper {

    public Fight toFight(FightRequest request) {
        return Fight.builder()
                .fightOrder(request.fightOrder())
                .isTitleFight(request.isTitleFight())
                .isClosed(request.isClosed())
                .isKo(request.isKo())
                .isDraw(request.isDraw())
                .weight(request.weight())
                .rounds(request.rounds())
                .minutesPerRound(request.minutesPerRound())
                .likes(request.likes())
                .winner(request.winner())
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
                .fightDate(
                        Optional.ofNullable(fight.getEvent())
                                .map(Event::getStartDate)
                                .orElse(null)
                )
                .fightOrder(fight.getFightOrder())
                .isTitleFight(fight.isTitleFight())
                .isClosed(fight.isClosed())
                .isKo(fight.isKo())
                .isDraw(fight.isDraw())
                .weight(fight.getWeight())
                .rounds(fight.getRounds())
                .minutesPerRound(fight.getMinutesPerRound())
                .likes(fight.getLikes())
                .winnerId(
                        Optional.ofNullable(fight.getWinner())
                                .map(FighterProfile::getId)
                                .orElse(null)
                )
                .winnerName(
                        Optional.ofNullable(fight.getWinner())
                                .map(FighterProfile::getFullName)
                                .orElse(null)
                )
                .blueCornerFighterId(
                        Optional.ofNullable(fight.getBlueCornerFighter())
                                .map(FighterProfile::getId)
                                .orElse(null)
                )
                .blueCornerFighterName(
                        Optional.ofNullable(fight.getBlueCornerFighter())
                                .map(FighterProfile::getFullName)
                                .orElse(null)
                )

                .blueCornerFighterClub(
                        Optional.ofNullable(fight.getBlueCornerFighter())
                                .map(FighterProfile::getClub)
                                .map(Club::getName)
                                .orElse(null)
                )
                .redCornerFighterId(
                        Optional.ofNullable(fight.getRedCornerFighter())
                                .map(FighterProfile::getId)
                                .orElse(null)
                )
                .redCornerFighterName(
                        Optional.ofNullable(fight.getRedCornerFighter())
                                .map(FighterProfile::getFullName)
                                .orElse(null)
                )
                .redCornerFighterClub(
                        Optional.ofNullable(fight.getRedCornerFighter())
                                .map(FighterProfile::getClub)
                                .map(Club::getName)
                                .orElse(null)
                )
                .eventId(
                        Optional.ofNullable(fight.getEvent())
                                .map(Event::getId)
                                .orElse(null)
                )
                .eventName(
                        Optional.ofNullable(fight.getEvent())
                                .map(Event::getName)
                                .orElse(null)
                )
                .categoryName(
                        Optional.ofNullable(fight.getCategory())
                                .map(Category::getName)
                                .orElse(null)
                )
                .categoryId(
                        Optional.ofNullable(fight.getCategory())
                                .map(Category::getId)
                                .orElse(null)
                )
                .styleName(
                        Optional.ofNullable(fight.getStyle())
                                .map(Style::getName)
                                .orElse(null)
                )
                .styleId(
                        Optional.ofNullable(fight.getStyle())
                                .map(Style::getId)
                                .orElse(null)
                )
                .build();
    }
}
