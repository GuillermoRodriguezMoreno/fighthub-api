package com.fighthub.fighthubapi.fighter_profile;

import org.springframework.stereotype.Service;
import com.fighthub.fighthubapi.fight.Fight;

import java.util.stream.Collectors;

@Service
public class FighterProfileMapper {

    public FighterProfile toFighterProfile(FighterProfileRequest request) {
        return FighterProfile.builder()
                .weight(request.weight())
                .height(request.height())
                .gender(request.gender())
                .biography(request.biography())
                .user(request.user())
                .styles(request.styles())
                .category(request.category())
                .club(request.club())
                .build();
    }

    public FighterProfileResponse toFighterProfileResponse(FighterProfile profile) {
        return FighterProfileResponse.builder()
                .id(profile.getId())
                .weight(profile.getWeight())
                .height(profile.getHeight())
                .gender(profile.getGender())
                .biography(profile.getBiography())
                .user(profile.getUser())
                .styles(profile.getStyles())
                .category(profile.getCategory())
                .club(profile.getClub())
                .clubsOwned(profile.getClubsOwned())
                .blueCornerFights(profile.getBlueCornerFights().stream()
                        .map(Fight::getId)
                        .collect(Collectors.toSet()))
                .redCornerFights(profile.getRedCornerFights().stream()
                        .map(Fight::getId)
                        .collect(Collectors.toSet()))
                .build();
    }
}
