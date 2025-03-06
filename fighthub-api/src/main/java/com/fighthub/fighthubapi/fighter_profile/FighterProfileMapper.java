package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.CategoryResponse;
import com.fighthub.fighthubapi.club.ClubResponse;
import com.fighthub.fighthubapi.style.StyleResponse;
import org.springframework.stereotype.Service;
import com.fighthub.fighthubapi.user.User;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class FighterProfileMapper {

    public FighterProfile toFighterProfile(FighterProfileRequest request) {
        return FighterProfile.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .dateOfBirth(request.dateOfBirth())
                .weight(request.weight())
                .height(request.height())
                .gender(request.gender())
                .biography(request.biography())
                .wins(request.wins())
                .losses(request.losses())
                .draws(request.draws())
                .kos(request.kos())
                .winsInARow(request.winsInARow())
                .location(request.location())
                .user(request.user())
                .styles(request.styles())
                .category(request.category())
                .club(request.club())
                .build();
    }

    public FighterProfileResponse toFighterProfileResponse(FighterProfile profile) {
        return FighterProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getFullName())
                .dateOfBirth(profile.getDateOfBirth())
                .weight(profile.getWeight())
                .height(profile.getHeight())
                .gender(profile.getGender())
                .biography(profile.getBiography())
                .wins(profile.getWins())
                .losses(profile.getLosses())
                .draws(profile.getDraws())
                .kos(profile.getKos())
                .winsInARow(profile.getWinsInARow())
                .location(profile.getLocation())
                .userId(
                        Optional.ofNullable(profile.getUser())
                                .map(User::getId)
                                .orElse(null)
                )
                .username(
                        Optional.ofNullable(profile.getUser())
                                .map(User::getNickname)
                                .orElse(null)
                )
                .email(
                        Optional.ofNullable(profile.getUser())
                                .map(User::getEmail)
                                .orElse(null)
                )
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
