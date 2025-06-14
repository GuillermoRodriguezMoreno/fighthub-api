package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FighterMatchmakingService {
    private final OpenAiService openAiService;
    private final FighterProfileRepository fighterProfileRepository;
    private final FighterMapper fighterMapper;

    public FighterMatchmakingService(OpenAiService openAiService,
                                     FighterProfileRepository fighterProfileRepository,
                                     FighterMapper fighterMapper) {
        this.openAiService = openAiService;
        this.fighterProfileRepository = fighterProfileRepository;
        this.fighterMapper = fighterMapper;
    }

    public Mono<List<OpponentRank>> rankOpponentsFor(Long fighterId) {
        return Mono.fromCallable(() -> fighterProfileRepository.findById(fighterId))
                .flatMap(optional -> optional
                        .map(profile -> Mono.just(fighterMapper.toFighter(profile)))
                        .orElseGet(() -> Mono.error(new NoSuchElementException("Fighter not found with ID: " + fighterId))))
                .zipWith(Mono.fromCallable(() -> fighterProfileRepository.findAll())
                        .map(list -> list.stream()
                                .map(fighterMapper::toFighter)
                                .filter(f -> !f.getId().equals(fighterId))
                                .toList()))
                .flatMap(tuple -> {
                    Fighter target = tuple.getT1();
                    List<Fighter> opponents = tuple.getT2();

                    String targetJson = openAiService.toJson(target);
                    String opponentsJson = openAiService.toJson(opponents);

                    return openAiService.rankOpponents(targetJson, opponentsJson);
                });
    }
}
