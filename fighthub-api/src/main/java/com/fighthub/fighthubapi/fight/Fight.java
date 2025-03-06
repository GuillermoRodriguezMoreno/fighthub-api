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
    private Integer fightOrder;
    private boolean isTitleFight;
    private boolean isClosed;
    private boolean isKo;
    private boolean isDraw;
    private double weight;
    private Integer rounds;
    private Integer minutesPerRound;
    private Long likes;

    @ManyToOne
    @JoinColumn(name = "winner_id")
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

    public void updateFighterStats() {
        if (this.isDraw) {
            if (this.blueCornerFighter != null) {
                this.blueCornerFighter.incrementDraws();
            }
            if (this.redCornerFighter != null) {
                this.redCornerFighter.incrementDraws();
            }
        } else if (this.winner != null) {
            this.winner.incrementWins();
            this.winner.incrementWinsInARow();
            if (this.isKo) {
                this.winner.incrementKos();
            }
            if (this.blueCornerFighter != null && !this.blueCornerFighter.equals(this.winner)) {
                this.blueCornerFighter.incrementLosses();
                this.blueCornerFighter.resetWinsInARow();
            }
            if (this.redCornerFighter != null && !this.redCornerFighter.equals(this.winner)) {
                this.redCornerFighter.incrementLosses();
                this.redCornerFighter.resetWinsInARow();
            }
        }
    }
}
