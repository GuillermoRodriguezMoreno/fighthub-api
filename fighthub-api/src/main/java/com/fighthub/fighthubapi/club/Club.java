package com.fighthub.fighthubapi.club;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.user.User;
import jakarta.persistence.*;
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
public class Club extends BaseEntity {
    private String name;
    private String address;
    private String email;
    private String description;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private FighterProfile owner;
    @OneToMany(mappedBy = "organizer")
    @JsonIgnore
    private Set<Event> eventsOrganized = new HashSet<>();
    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private Set<FighterProfile> members = new HashSet<>();
}
