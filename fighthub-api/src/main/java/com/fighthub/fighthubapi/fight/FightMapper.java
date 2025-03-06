package com.fighthub.fighthubapi.fight;

import org.springframework.stereotype.Service;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
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
                .likes(request.likes())
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
                .likes(fight.getLikes())
                .blueCornerFighterId(
                        Optional.ofNullable(fight.getBlueCornerFighter())
                                .map(FighterProfile::getId)
                                .orElse(null)
                )
                .blueCornerFighterName(
                        Optional.ofNullable(fight.getBlueCornerFighter())
                                .map(FighterProfile::getUser)
                                .map(User::getFullName)
                                .orElse(null)
                )

                .blueCornerFighterClub(
                        Optional.ofNullable(fight.getBlueCornerFighter())
                                .map(FighterProfile::getClub)
                                .map(Club::getName)
                                .orElse(null)
                )
                .redCornerFighterName(
                        Optional.ofNullable(fight.getRedCornerFighter())
                                .map(FighterProfile::getUser)
                                .map(User::getFullName)
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
                .category(
                        Optional.ofNullable(fight.getCategory())
                                .map(Category::getName)
                                .orElse(null)
                )
                .style(
                        Optional.ofNullable(fight.getStyle())
                                .map(Style::getName)
                                .orElse(null)
                )
                .build();
    }
}
