package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.category.Category;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.user.User;
import jakarta.persistence.*;
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
    private Set<Style> styles;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
