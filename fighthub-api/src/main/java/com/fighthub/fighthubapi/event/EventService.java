package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.club.ClubRepository;
import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public Long saveEvent(EventRequest request) {
        Event event = eventMapper.toEvent(request);
        return eventRepository.save(event).getId();
    }

    public EventResponse findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::toEventResponse)
                .orElseThrow(() -> new EntityNotFoundException("event not found with id: " + eventId));
    }

    public PageResponse<EventResponse> findAllEvents(Integer page, Integer size, String orderBy) {
        Sort sort = Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Event> events = eventRepository.findAll(pageable);
        List<EventResponse> eventResponse = events.stream()
                .map(eventMapper::toEventResponse)
                .toList();
        return new PageResponse<>(
                eventResponse,
                events.getNumber(),
                events.getSize(),
                events.getTotalElements(),
                events.getTotalPages(),
                events.isFirst(),
                events.isLast());
    }

    public EventResponse updateEvent(Long eventId, EventRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("event not found with id: " + eventId));
        Club organizer = clubRepository.findById(request.organizer().getId())
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + request.organizer().getId()));
        event.setName(request.name());
        event.setDescription(request.description());
        event.setAddress(request.address());
        event.setStartDate(request.startDate());
        event.setEndDate(request.endDate());
        event.setOrganizer(organizer);

        return eventMapper.toEventResponse(eventRepository.save(event));
    }
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public PageResponse<EventResponse> findEventsByOrganizerProfile(String organizerMail, Integer page, Integer size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Long organizerId = userRepository.findByEmail(organizerMail)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with email: " + organizerMail))
                .getId();
        Page<Event> events = eventRepository.findAllByOrganizerProfileId(organizerId ,pageable);
        List<EventResponse> eventResponse = events.stream()
                .map(eventMapper::toEventResponse)
                .toList();
        return new PageResponse<>(
                eventResponse,
                events.getNumber(),
                events.getSize(),
                events.getTotalElements(),
                events.getTotalPages(),
                events.isFirst(),
                events.isLast());
    }

    public Event incrementLikes(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("event not found with id: " + eventId));
        event.incrementLikes();
        return eventRepository.save(event);
    }
}
