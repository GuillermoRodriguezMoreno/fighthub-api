package com.fighthub.fighthubapi.fighter_profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import com.fighthub.fighthubapi.location.Location;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FighterProfile extends BaseEntity {
    private double weight;
    private int height;
    private String gender;
    private String biography;
    @Column(columnDefinition = "integer default 0")
    private Integer wins;
    @Column(columnDefinition = "integer default 0")
    private Integer losses;
    @Column(columnDefinition = "integer default 0")
    private Integer draws;
    @Column(columnDefinition = "integer default 0")
    private Integer ko;
    @Column(name = "wins_in_a_row", columnDefinition = "integer default 0")
    private int winsInARow;

    @Embedded
    private Location location;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "fighter_profile_styles",
            joinColumns = @JoinColumn(name = "fighters_id"),
            inverseJoinColumns = @JoinColumn(name = "styles_id")
    )
    private Set<Style> styles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne()
    @JoinColumn(name = "club_id")
    private Club club;
    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Set<Club> clubsOwned = new HashSet<>();
    @OneToMany(mappedBy = "blueCornerFighter", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Fight> blueCornerFights = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "redCornerFighter", cascade = CascadeType.MERGE)
    private Set<Fight> redCornerFights = new HashSet<>();
}
