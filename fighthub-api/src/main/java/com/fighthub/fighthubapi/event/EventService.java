package com.fighthub.fighthubapi.event;

import com.fighthub.fighthubapi.common.PageResponse;
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

    public PageResponse<EventResponse> findAllEvents(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate", "name").descending());
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
        event.setName(request.name());
        event.setDescription(request.description());
        event.setAddress(request.address());
        event.setStartDate(request.startDate());
        event.setEndDate(request.endDate());
        event.setOrganizer(request.organizer());

        return eventMapper.toEventResponse(eventRepository.save(event));
    }
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
