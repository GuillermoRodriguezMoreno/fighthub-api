package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FighterProfileResponse {
    private Long id;
    private double weight;
    private int height;
    private String gender;
    private String biography;
    private User user;
    private Set<Style> styles;
    private Category category;
    private Club club;
    private Set<Club> clubsOwned;
    private Set<Long> blueCornerFights;
    private Set<Long> redCornerFights;
}
