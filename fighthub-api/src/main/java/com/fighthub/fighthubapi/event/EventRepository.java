package com.fighthub.fighthubapi.event;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.organizer.owner.id = :organizerId")
    Page<Event> findAllByOrganizerId(Long organizerId, Pageable pageable);
}
