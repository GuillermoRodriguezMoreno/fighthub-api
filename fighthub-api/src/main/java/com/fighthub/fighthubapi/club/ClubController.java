package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.event.EventResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Clubs")
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<Long> saveClub(
            @Valid @RequestBody ClubRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(clubService.saveClub(request));
    }
    @GetMapping("{club-id}")
    public ResponseEntity<ClubResponse> findClubById(
            @PathVariable("club-id") Long clubId
    ) {
        return ResponseEntity.ok(clubService.findClubById(clubId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<ClubResponse>> findAllClubs(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "name" ,required = false) String orderBy
    ){
        return ResponseEntity.ok(clubService.findAllClubs(page, size, orderBy));
    }
    @PutMapping("{club-id}")
    public ResponseEntity<ClubResponse> updateClub(
            @PathVariable("club-id") Long clubId,
            @Valid @RequestBody ClubRequest request
    ) {
        return ResponseEntity.ok(clubService.updateClub(clubId, request));
    }

    @PostMapping("{club-id}/join")
    public ResponseEntity<String> joinClub(
            @PathVariable("club-id") Long clubId,
            @RequestBody Long fighterId
    ) {
        clubService.addFighterProfileToClub(clubId, fighterId);
        return ResponseEntity.ok("Fighter with id: " + fighterId + " joined club with id: " + clubId);
    }

    @DeleteMapping("{club-id}")
    public ResponseEntity<Void> deleteClub(
            @PathVariable("club-id") Long clubId
    ) {
        clubService.deleteClub(clubId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{owner-email}")
    public ResponseEntity<List<ClubResponse>> findClubsByOwner(
            @PathVariable("owner-email") String ownerEmail
    ) {
        return ResponseEntity.ok(clubService.findClubsByOwnerId(ownerEmail));
    }

    @GetMapping("/popular")
    public ResponseEntity<PageResponse<ClubResponse>> findPopularClubs(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size
    ) {
        return ResponseEntity.ok(clubService.findClubsOrderByMembersSize(page, size));
    }
    
    @GetMapping("/fighter/{fighter-id}")
    public ResponseEntity<ClubResponse> findClubsByFighter(
            @PathVariable("fighter-id") Long fighterId
    ) {
        return ResponseEntity.ok(clubService.findClubByFighterProfileId(fighterId));
    }

    @PostMapping("/{club_id}/upload-picture")
    public ResponseEntity<ClubResponse> updateProfilePicture(
            @PathVariable("club_id") Long clubId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            ClubResponse updatedProfile = clubService.uploadProfilePicture(clubId, file);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
