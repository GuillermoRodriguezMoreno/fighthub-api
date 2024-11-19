package com.fighthub.fighthubapi.category;

import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<FighterProfile> fighterProfiles = new HashSet<>();
    @OneToMany(mappedBy = "category")
    private Set<Fight> fights = new HashSet<>();
}
