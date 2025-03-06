package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Tag(name = "FighterProfiles")
@RequestMapping("/fighter-profiles")
public class FighterProfileController {

    private final FighterProfileService fighterProfileservice;

    @PostMapping
    public ResponseEntity<Long> saveFighterProfile(
            @Valid @RequestBody FighterProfileRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(fighterProfileservice.saveFighterProfile(request));
    }
    @GetMapping("{fighterProfile-id}")
    public ResponseEntity<FighterProfileResponse> findFighterProfileById(
            @PathVariable("fighterProfile-id") Long fighterProfileId
    ) {
        return ResponseEntity.ok(fighterProfileservice.findFighterProfileById(fighterProfileId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<FighterProfileResponse>> findAllFighterProfiles(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy
    ){
        return ResponseEntity.ok(fighterProfileservice.findAllFighterProfiles(page, size, orderBy));
    }
    @PutMapping("{fighterProfile-id}")
    public ResponseEntity<FighterProfileResponse> updateFighterProfile(
            @PathVariable("fighterProfile-id") Long fighterProfileId,
            @Valid @RequestBody FighterProfileRequest request
    ) {
        return ResponseEntity.ok(fighterProfileservice.updateFighterProfile(fighterProfileId, request));
    }
    @DeleteMapping("{fighterProfile-id}")
    public ResponseEntity<Void> deleteFighterProfile(
            @PathVariable("fighterProfile-id") Long fighterProfileId
    ) {
        fighterProfileservice.deleteFighterProfile(fighterProfileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/club/{club-id}")
    public ResponseEntity<PageResponse<FighterProfileResponse>> findFighterProfilesByClub(
            @PathVariable("club-id") Long clubId,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ) {
        return ResponseEntity.ok(fighterProfileservice.findAllFighterProfilesByClubId(clubId, page, size));
    }

    @GetMapping("/{fighter_id}/nearest")
    public ResponseEntity<Set<FighterProfileResponse>> findNearestFighterProfiles(
            @PathVariable("fighter_id") Long fighterId,
            @RequestParam(name = "radius", defaultValue = "50", required = false) Long radius
    ) {
        return ResponseEntity.ok(fighterProfileservice.findAllFighterNearestToLocation(fighterId, radius));
    }
}
