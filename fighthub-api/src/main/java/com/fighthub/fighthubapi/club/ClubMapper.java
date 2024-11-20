package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

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
                .ownerId(club.getOwner().getId())
                .ownerName(club.getOwner().getUser().getFullName())
                .ownerUsername(club.getOwner().getUser().getUsername())
                .ownerEmail(club.getOwner().getUser().getEmail())
                .build();
    }
}
