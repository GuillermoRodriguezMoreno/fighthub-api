package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.club.Club;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private Club organizer;
    private Set<Long> fights;
}
