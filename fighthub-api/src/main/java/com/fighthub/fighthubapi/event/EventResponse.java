package com.fighthub.fighthubapi.event;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long organizerId;
    private String organizerName;
    private String organizerAddress;
    private String organizerEmail;
    private String organizerPhone;
}
