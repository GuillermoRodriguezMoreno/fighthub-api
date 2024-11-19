package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.fight.Fight;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

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
                .organizer(event.getOrganizer())
                .fights(
                        event.getFights()
                                .stream()
                                .map(Fight::getId)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
