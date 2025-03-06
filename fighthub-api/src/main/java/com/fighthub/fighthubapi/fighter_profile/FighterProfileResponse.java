package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.category.CategoryResponse;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.club.ClubResponse;
import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.location.Location;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.style.StyleResponse;
import com.fighthub.fighthubapi.user.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FighterProfileResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private double weight;
    private int height;
    private String gender;
    private String biography;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer kos;
    private Integer winsInARow;
    private Location location;
    private Long userId;
    private String username;
    private String email;
    private Set<StyleResponse> styles;
    private CategoryResponse category;
    private ClubResponse club;

}
