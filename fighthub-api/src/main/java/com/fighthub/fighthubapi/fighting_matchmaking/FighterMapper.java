package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.style.Style;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class FighterMapper {
    public Fighter toFighter(FighterProfile profile) {
        return Fighter.builder()
                .id(profile.getId())
                .name(profile.getFullName())
                .dateOfBirth(profile.getDateOfBirth())
                .weight(profile.getWeight())
                .height(profile.getHeight())
                .gender(profile.getGender())
                .wins(profile.getWins())
                .losses(profile.getLosses())
                .draws(profile.getDraws())
                .kos(profile.getKos())
                .winsInARow(profile.getWinsInARow())
                .location(profile.getLocation())
                .styles(Optional.of(profile.getStyles())
                        .orElse(Collections.emptySet())
                        .stream().map(Style::getName)
                        .collect(Collectors.toList()))
                .category(Optional.ofNullable(profile.getCategory())
                        .map(Category::getName)
                        .orElse(null))
                .build();
    }
}
