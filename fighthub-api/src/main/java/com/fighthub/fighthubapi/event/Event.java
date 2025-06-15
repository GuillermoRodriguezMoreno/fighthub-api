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

import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long likes;
    private String profilePicture;

    @ManyToOne()
    @JoinColumn(name = "organizer_id")
    private Club organizer;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Fight> fights = new HashSet<>();

    public void incrementLikes() {
        this.likes++;
    }
}
