package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fight extends BaseEntity {
    private int fightOrder;
    private boolean isTitleFight;
    private boolean isClosed;
    private boolean isKo;
    private double weight;
    private int rounds;
    private int minutesPerRound;
    private Long likes;

    @OneToOne
    private FighterProfile winner;
    @ManyToOne
    @JoinColumn(name = "blue_corner_fighter_id")
    private FighterProfile blueCornerFighter;
    @ManyToOne
    @JoinColumn(name = "red_corner_fighter_id")
    private FighterProfile redCornerFighter;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    public void incrementLikes() {
        this.likes++;
    }
}
