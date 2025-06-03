package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fights")
@RequestMapping("/fights")
public class FightController {

    private final FightService fightservice;

    @PostMapping
    public ResponseEntity<Long> saveFight(
            @Valid @RequestBody FightRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(fightservice.saveFight(request));
    }
    @GetMapping("{fight-id}")
    public ResponseEntity<FightResponse> findFightById(
            @PathVariable("fight-id") Long fightId
    ) {
        return ResponseEntity.ok(fightservice.findFightById(fightId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<FightResponse>> findAllFights(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "event.startDate", required = false) String orderBy
    ){
        return ResponseEntity.ok(fightservice.findAllFights(page, size, orderBy));
    }
    @PutMapping("{fight-id}")
    public ResponseEntity<FightResponse> updateFight(
            @PathVariable("fight-id") Long fightId,
            @Valid @RequestBody FightRequest request
    ) {
        return ResponseEntity.ok(fightservice.updateFight(fightId, request));
    }
    @DeleteMapping("{fight-id}")
    public ResponseEntity<Void> deleteFight(
            @PathVariable("fight-id") Long fightId
    ) {
        fightservice.deleteFight(fightId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fighter/{fighter-id}")
    public ResponseEntity<PageResponse<FightResponse>> findFightsByFighter(
            @PathVariable("fighter-id") Long fighterId,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "event.startDate", required = false) String orderBy
    ) {
        return ResponseEntity.ok(fightservice.findFightsByFighterId(fighterId, page, size, orderBy));
    }

    @GetMapping("/event/{event-id}")
    public ResponseEntity<PageResponse<FightResponse>> findFightsByEvent(
            @PathVariable("event-id") Long eventId,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "fightOrder", required = false) String orderBy
    ) {
        return ResponseEntity.ok(fightservice.findFightsByEventId(eventId, page, size, orderBy));
    }

    @GetMapping("/popular")
    public ResponseEntity<PageResponse<FightResponse>> findPopularFights(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size
    ) {
        return ResponseEntity.ok(fightservice.findFightsPopular(page, size));
    }

    @PostMapping("{fight_id}/like")
    public ResponseEntity<Fight> likeFight(
            @PathVariable("fight_id") Long fightId
    ) {
        Fight fight = fightservice.incrementLikes(fightId);
        return ResponseEntity.ok(fight);
    }
}
