package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinColumn(name = "style_id")
    private Set<Style> styles;

}
