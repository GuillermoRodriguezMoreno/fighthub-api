package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.common.BaseEntity;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    @OneToOne
    private User coach;
    @OneToMany(mappedBy = "club")
    private Set<Event> eventsOrganized;
    @OneToMany(mappedBy = "club")
    private Set<User> members;
}
