package com.fighthub.fighthubapi.style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Style extends BaseEntity {

        private String name;

        @ManyToMany(mappedBy = "styles")
        @JsonIgnore
        private Set<FighterProfile> fighters = new HashSet<>();
        @OneToMany(mappedBy = "style")
        @JsonIgnore
        private Set<Fight> fights = new HashSet<>();
}
