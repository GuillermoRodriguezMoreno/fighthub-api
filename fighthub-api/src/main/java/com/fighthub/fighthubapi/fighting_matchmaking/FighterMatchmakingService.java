package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fighthub.fighthubapi.fighter_profile.FighterProfileMapper;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FighterMatchmakingService {
    private final OpenAiService openAiService;
    private final FighterProfileRepository fighterProfileRepository;
    private final FighterMapper fighterMapper;
    private final FighterProfileMapper fighterProfileMapper;

    public FighterMatchmakingService(OpenAiService openAiService,
                                     FighterProfileRepository fighterProfileRepository,
                                     FighterMapper fighterMapper,
                                     FighterProfileMapper fighterProfileMapper) {
        this.openAiService = openAiService;
        this.fighterProfileRepository = fighterProfileRepository;
        this.fighterMapper = fighterMapper;
        this.fighterProfileMapper = fighterProfileMapper;
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

    @Transactional
    public List<FighterProfileResponse> getMatchesForFighter(Long fighterId) {
        Mono<List<OpponentRank>> opponentRanksMono = rankOpponentsFor(fighterId);

        List<FighterProfileResponse> opponentProfiles = opponentRanksMono
                .flatMapMany(opponentRanks -> {
                    List<Long> opponentIds = opponentRanks.stream()
                            .map(OpponentRank::getId)
                            .toList();
                    return Mono.fromCallable(() -> fighterProfileRepository.findAllById(opponentIds))
                            .map(profiles -> {
                                Map<Long, Double> affinityMap = opponentRanks.stream()
                                        .collect(Collectors.toMap(OpponentRank::getId, OpponentRank::getScore));
                                return profiles.stream()
                                        .map(profile -> {
                                            FighterProfileResponse response = fighterProfileMapper.toFighterProfileResponse(profile);
                                            response.setAffinity(affinityMap.get(profile.getId()));
                                            return response;
                                        })
                                        .toList();
                            });
                })
                .flatMapIterable(profiles -> profiles)
                .sort((p1, p2) -> Double.compare(p2.getAffinity(), p1.getAffinity()))
                .collectList()
                .block();

        return opponentProfiles;
    }
}
