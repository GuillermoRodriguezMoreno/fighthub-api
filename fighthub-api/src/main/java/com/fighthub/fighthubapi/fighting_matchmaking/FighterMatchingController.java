package com.fighthub.fighthubapi.fighting_matchmaking;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "FighterMatching")
@RequestMapping("/fighter-matching")
public class FighterMatchingController {

    private final FighterMatchmakingService matchmakingService;

    @GetMapping("/{targetId}")
    public Mono<ResponseEntity<List<OpponentRank>>> findMatches(@PathVariable Long targetId) {
        Mono<List<OpponentRank>> opponentsMono = matchmakingService.rankOpponentsFor(targetId);
        Mono<ResponseEntity<List<OpponentRank>>> responseEntityMono =
                opponentsMono.map(ResponseEntity::ok)
                                   .onErrorReturn(ResponseEntity.notFound().build());
        return responseEntityMono;
    }
}
