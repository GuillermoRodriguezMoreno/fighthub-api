package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Events")
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Long> saveEvent(
            @Valid @RequestBody EventRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(eventService.saveEvent(request));
    }
    @GetMapping("{event-id}")
    public ResponseEntity<EventResponse> findEventById(
            @PathVariable("event-id") Long eventId
    ) {
        return ResponseEntity.ok(eventService.findEventById(eventId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<EventResponse>> findAllEvents(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "startDate", required = false) String orderBy
    ){
        return ResponseEntity.ok(eventService.findAllEvents(page, size, orderBy));
    }
    @PutMapping("{event-id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable("event-id") Long eventId,
            @Valid @RequestBody EventRequest request
    ) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, request));
    }
    @DeleteMapping("{event-id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable("event-id") Long eventId
    ) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("organizer/{organizer-id}")
    public ResponseEntity<PageResponse<EventResponse>> findEventsByOrganizer(
            @PathVariable("organizer-id") String organizerMail,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "startDate", required = false) String orderBy
    ) {
        return ResponseEntity.ok(eventService.findEventsByOrganizerProfile(organizerMail, page, size, orderBy));
    }

    @PostMapping("{event_id}/like")
    public ResponseEntity<Event> likeEvent(
            @PathVariable("event_id") Long eventId
    ) {
        Event event = eventService.incrementLikes(eventId);
        return ResponseEntity.ok(event);
    }
}
