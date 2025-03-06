package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.user.User;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClubMapper {

    public Club toClub(ClubRequest request) {
        return Club.builder()
                .id(request.id())
                .name(request.name())
                .address(request.address())
                .email(request.email())
                .description(request.description())
                .phone(request.phone())
                .owner(request.owner()) // Aquí suponemos que owner es de tipo FighterProfile en el request
                .build();
    }

    public ClubResponse toClubResponse(Club club) {
        return ClubResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .address(club.getAddress())
                .email(club.getEmail())
                .description(club.getDescription())
                .phone(club.getPhone())
                .ownerId(
                        Optional.ofNullable(club.getOwner())
                                .map(FighterProfile::getId)
                                .orElse(null)
                )
                .ownerName(
                        Optional.ofNullable(club.getOwner())
                                .map(FighterProfile::getFullName)
                                .orElse(null)
                )
                .ownerUsername(
                        Optional.ofNullable(club.getOwner())
                                .map(FighterProfile::getUser)
                                .map(User::getUsername)
                                .orElse(null)
                )
                .ownerEmail(
                        Optional.ofNullable(club.getOwner())
                                .map(FighterProfile::getUser)
                                .map(User::getEmail)
                                .orElse(null)
                )

                .build();
    }
}
