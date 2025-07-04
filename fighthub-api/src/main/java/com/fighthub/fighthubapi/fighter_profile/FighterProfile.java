package com.fighthub.fighthubapi.fighter_profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.picture.Picture;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import com.fighthub.fighthubapi.location.Location;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FighterProfile {

    @Id
    private Long id;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;
    @LastModifiedBy
    private String lastUpdatedBy;

    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private double weight;
    private int height;
    private String gender;
    private String biography;
    private String profilePicture;

    @Column(columnDefinition = "integer default 0")
    private Integer wins = 0;
    @Column(columnDefinition = "integer default 0")
    private Integer losses = 0;
    @Column(columnDefinition = "integer default 0")
    private Integer draws = 0;
    @Column(columnDefinition = "integer default 0")
    private Integer kos = 0;
    @Column(name = "wins_in_a_row", columnDefinition = "integer default 0")
    private Integer winsInARow = 0;

    @Embedded
    private Location location;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
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
    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Club> clubsOwned = new HashSet<>();
    @OneToMany(mappedBy = "blueCornerFighter", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Fight> blueCornerFights = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "redCornerFighter", cascade = CascadeType.MERGE)
    private Set<Fight> redCornerFights = new HashSet<>();
    // TODO: Implement this
    @JsonIgnore
    @OneToMany(mappedBy = "fighterProfile", cascade = CascadeType.ALL)
    private Set<Picture> pictures = new HashSet<>();

    public String getFullName() {
        if (firstname == null && lastname == null) {
            return "";
        }
        return (firstname + " " + lastname).trim();
    }

    public void incrementWinsInARow() {
        this.winsInARow++;
    }

    public void resetWinsInARow() {
        this.winsInARow = 0;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public void incrementDraws() {
        this.draws++;
    }

    public void incrementKos() {
        this.kos++;
    }

}
