package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> deleteEvent(
            @PathVariable("event-id") Long eventId
    ) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event with id: " + eventId + " deleted");
    }

    @GetMapping("organizer/{organizer-id}")
    public ResponseEntity<PageResponse<EventResponse>> findEventsByOrganizer(
            @PathVariable("organizer-id") String organizerMail,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "startDate", required = false) String orderBy
    ) {
        PageResponse<EventResponse> response = eventService.findEventsByOrganizer(organizerMail, page, size, orderBy);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{event_id}/profile-picture")
    public ResponseEntity<EventResponse> updateProfilePicture(
            @PathVariable("event_id") Long eventId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            EventResponse updatedProfile = eventService.uploadProfilePicture(eventId, file);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("{event_id}/like")
    public ResponseEntity<Event> likeEvent(
            @PathVariable("event_id") Long eventId
    ) {
        Event event = eventService.incrementLikes(eventId);
        return ResponseEntity.ok(event);
    }
}
