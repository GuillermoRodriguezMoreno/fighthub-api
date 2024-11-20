package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.CategoryResponse;
import com.fighthub.fighthubapi.club.ClubResponse;
import com.fighthub.fighthubapi.style.StyleResponse;
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
                .userId(profile.getUser().getId())
                .name(profile.getUser().getFullName())
                .username(profile.getUser().getNickname())
                .email(profile.getUser().getEmail())
                .dateOfBirth(profile.getUser().getDateOfBirth())
                .styles(profile.getStyles().stream().map(style -> StyleResponse.builder()
                        .id(style.getId())
                        .name(style.getName())
                        .build()).collect(Collectors.toSet()))
                .category(CategoryResponse.builder()
                        .id(profile.getCategory().getId())
                        .name(profile.getCategory().getName())
                        .build())
                .club(profile.getClub() != null ? ClubResponse.builder()
                        .id(profile.getClub().getId())
                        .name(profile.getClub().getName())
                        .build() : null)
                .build();
    }
}
