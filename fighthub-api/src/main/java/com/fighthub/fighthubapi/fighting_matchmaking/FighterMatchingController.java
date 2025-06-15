package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fighthub.fighthubapi.fighter_profile.FighterProfileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Tag(name = "FighterMatching")
@RequestMapping("/fighter-matching")
public class FighterMatchingController {

    private final FighterMatchmakingService matchmakingService;

    @GetMapping("/{targetId}")
    public ResponseEntity<List<FighterProfileResponse>> findMatches(@PathVariable Long targetId) {
        try {
            List<FighterProfileResponse> opponentProfiles = matchmakingService.getMatchesForFighter(targetId);
            return ResponseEntity.ok(opponentProfiles);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
