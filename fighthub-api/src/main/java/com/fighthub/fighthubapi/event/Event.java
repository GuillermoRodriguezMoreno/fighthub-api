package com.fighthub.fighthubapi.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.fight.Fight;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event extends BaseEntity {
    private String name;
    private String description;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne()
    @JoinColumn(name = "organizer_id", nullable = false)
    private Club organizer;
    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Fight> fights = new HashSet<>();
}
