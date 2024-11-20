package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubResponse {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String description;
    private String phone;
    private Long ownerId;
    private String ownerName;
    private String ownerUsername;
    private String ownerEmail;
}
