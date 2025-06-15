package com.fighthub.fighthubapi.picture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
// TODO: Implement this
public class Picture extends BaseEntity {
    private String url;

    @ManyToOne
    @JoinColumn(name = "fighter_profile_id", nullable = false)
    private FighterProfile fighterProfile;
}
