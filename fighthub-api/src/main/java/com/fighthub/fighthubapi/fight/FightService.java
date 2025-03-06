package com.fighthub.fighthubapi.fight;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.event.EventRepository;
import com.fighthub.fighthubapi.fight.*;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
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
public class FightService {

    private final FightRepository fightRepository;
    private final FighterProfileRepository fighterProfileRepository;
    private final EventRepository eventRepository;
    private final FightMapper fightMapper;

    public Long saveFight(FightRequest request) {
        Fight fight = fightMapper.toFight(request);
        return fightRepository.save(fight).getId();
    }

    public FightResponse findFightById(Long fightId) {
        return fightRepository.findById(fightId)
                .map(fightMapper::toFightResponse)
                .orElseThrow(() -> new EntityNotFoundException("fight not found with id: " + fightId));
    }

    public PageResponse<FightResponse> findAllFights(Integer page, Integer size, String orderBy) {
        Sort sort = Sort.by("startDate", "name").descending();
        if (orderBy != null && !orderBy.isEmpty()) {
            sort = Sort.by(orderBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Fight> fights = fightRepository.findAll(pageable);
        List<FightResponse> fightResponse = fights.stream()
                .map(fightMapper::toFightResponse)
                .toList();
        return new PageResponse<>(
                fightResponse,
                fights.getNumber(),
                fights.getSize(),
                fights.getTotalElements(),
                fights.getTotalPages(),
                fights.isFirst(),
                fights.isLast());
    }
    public FightResponse updateFight(Long fightId, FightRequest request) {
        Fight fight = fightRepository.findById(fightId)
                .orElseThrow(() -> new EntityNotFoundException("fight not found with id: " + fightId));
        FighterProfile blueCornerFighter = fighterProfileRepository.findById(request.blueCornerFighter().getId())
                .orElseThrow(() -> new EntityNotFoundException("fighter not found with id: " + request.blueCornerFighter().getId()));
        FighterProfile redCornerFighter = fighterProfileRepository.findById(request.redCornerFighter().getId())
                .orElseThrow(() -> new EntityNotFoundException("fighter not found with id: " + request.redCornerFighter().getId()));
        Event event = eventRepository.findById(request.event().getId())
                .orElseThrow(() -> new EntityNotFoundException("event not found with id: " + request.event().getId()));
        fight.setFightOrder(request.fightOrder());
        fight.setTitleFight(request.isTitleFight());
        fight.setClosed(request.isClosed());
        fight.setWeight(request.weight());
        fight.setRounds(request.rounds());
        fight.setMinutesPerRound(request.minutesPerRound());
        fight.setBlueCornerFighter(blueCornerFighter);
        fight.setRedCornerFighter(redCornerFighter);
        fight.setEvent(event);
        fight.setCategory(request.category());
        fight.setStyle(request.style());
        fight.setLikes(request.likes());

        return fightMapper.toFightResponse(fightRepository.save(fight));
    }
    public void deleteFight(Long fightId) {
        fightRepository.deleteById(fightId);
    }

    public PageResponse<FightResponse> findFightsByFighterId(Long fighterId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("event").descending());
        Page<Fight> fights = fightRepository.findAllByBlueCornerFighterIdOrRedCornerFighterId(fighterId, pageable);
        List<FightResponse> fightResponse = fights.stream()
                .map(fightMapper::toFightResponse)
                .toList();
        return new PageResponse<>(
                fightResponse,
                fights.getNumber(),
                fights.getSize(),
                fights.getTotalElements(),
                fights.getTotalPages(),
                fights.isFirst(),
                fights.isLast());
    }

    public PageResponse<FightResponse> findFightsByEventId(Long eventId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fightOrder").descending());
        Page<Fight> fights = fightRepository.findAllByEventId(eventId, pageable);
        List<FightResponse> fightResponse = fights.stream()
                .map(fightMapper::toFightResponse)
                .toList();
        return new PageResponse<>(
                fightResponse,
                fights.getNumber(),
                fights.getSize(),
                fights.getTotalElements(),
                fights.getTotalPages(),
                fights.isFirst(),
                fights.isLast());
    }

    public Fight incrementLikes(Long fightId) {
        Fight fight = fightRepository.findById(fightId)
                .orElseThrow(() -> new EntityNotFoundException("fight not found with id: " + fightId));
        fight.incrementLikes();
        return fightRepository.save(fight);
    }
}
