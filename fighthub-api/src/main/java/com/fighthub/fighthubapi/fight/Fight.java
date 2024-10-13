package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fight extends BaseEntity {

    private int fightOrder;
    private boolean isTitleFight;
    private boolean isClosed;
    private double weight;
    private int rounds;
    private int minutesPerRound;

    @ManyToOne
    @JoinColumn(name = "blue_corner_fighter_id")
    private User blueCornerFighter;
    @ManyToOne
    @JoinColumn(name = "red_corner_fighter_id")
    private User redCornerFighter;
}
