package com.fighthub.fighthubapi.event;

import org.springframework.stereotype.Service;

import java.util.Optional;
import com.fighthub.fighthubapi.club.Club;
@Service
public class EventMapper {

    public Event toEvent(EventRequest request) {
        return Event.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .address(request.address())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .organizer(request.organizer())
                .build();
    }

    public EventResponse toEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .address(event.getAddress())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .organizerId(
                        Optional.ofNullable(event.getOrganizer())
                                .map(Club::getId)
                                .orElse(null)
                )
                .organizerName(
                        Optional.ofNullable(event.getOrganizer())
                                .map(Club::getName)
                                .orElse(null)
                )
                .organizerAddress(
                        Optional.ofNullable(event.getOrganizer())
                                .map(Club::getAddress)
                                .orElse(null)
                )
                .organizerEmail(
                        Optional.ofNullable(event.getOrganizer())
                                .map(Club::getEmail)
                                .orElse(null)
                )
                .organizerPhone(
                        Optional.ofNullable(event.getOrganizer())
                                .map(Club::getPhone)
                                .orElse(null)
                )
                .build();
    }
}
