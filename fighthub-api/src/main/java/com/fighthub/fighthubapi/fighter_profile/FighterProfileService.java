package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FighterProfileService {

    private final FighterProfileRepository fighterProfileRepository;
    private final FighterProfileMapper fighterProfileMapper;

    public Long saveFighterProfile(FighterProfileRequest request) {
        FighterProfile fighterProfile = fighterProfileMapper.toFighterProfile(request);
        return fighterProfileRepository.save(fighterProfile).getId();
    }

    public FighterProfileResponse findFighterProfileById(Long fighterProfileId) {
        return fighterProfileRepository.findById(fighterProfileId)
                .map(fighterProfileMapper::toFighterProfileResponse)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterProfileId));
    }

    public PageResponse<FighterProfileResponse> findAllFighterProfiles(Integer page, Integer size, String orderBy) {
        Sort sort = Sort.by("weight").descending();
        if (orderBy != null && !orderBy.isEmpty()) {
            sort = Sort.by(orderBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FighterProfile> fighterProfiles = fighterProfileRepository.findAll(pageable);
        List<FighterProfileResponse> fighterProfileResponse = fighterProfiles.stream()
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();
        return new PageResponse<>(
                fighterProfileResponse,
                fighterProfiles.getNumber(),
                fighterProfiles.getSize(),
                fighterProfiles.getTotalElements(),
                fighterProfiles.getTotalPages(),
                fighterProfiles.isFirst(),
                fighterProfiles.isLast());
    }
    public FighterProfileResponse updateFighterProfile(Long fighterProfileId, FighterProfileRequest request) {
        FighterProfile fighterProfile = fighterProfileRepository.findById(fighterProfileId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterProfileId));
        fighterProfile.setFirstname(request.firstname());
        fighterProfile.setLastname(request.lastname());
        fighterProfile.setDateOfBirth(request.dateOfBirth());
        fighterProfile.setWeight(request.weight());
        fighterProfile.setHeight(request.height());
        fighterProfile.setGender(request.gender());
        Optional.ofNullable(request.biography()).ifPresent(fighterProfile::setBiography);
        fighterProfile.setWins(request.wins());
        fighterProfile.setLosses(request.losses());
        fighterProfile.setDraws(request.draws());
        fighterProfile.setKos(request.kos());
        fighterProfile.setWinsInARow(request.winsInARow());
        fighterProfile.setUser(request.user());
        fighterProfile.setStyles(request.styles());
        fighterProfile.setCategory(request.category());
        Optional.ofNullable(request.club()).ifPresent(fighterProfile::setClub);



        return fighterProfileMapper.toFighterProfileResponse(fighterProfileRepository.save(fighterProfile));
    }
    public void deleteFighterProfile(Long fighterProfileId) {
        fighterProfileRepository.deleteById(fighterProfileId);
    }

    public PageResponse<FighterProfileResponse> findAllFighterProfilesByClubId(Long clubId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("weight").descending());
        Page<FighterProfile> fighterProfiles = fighterProfileRepository.findAllByClubId(clubId, pageable);
        List<FighterProfileResponse> fighterProfileResponse = fighterProfiles.stream()
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();
        return new PageResponse<>(
                fighterProfileResponse,
                fighterProfiles.getNumber(),
                fighterProfiles.getSize(),
                fighterProfiles.getTotalElements(),
                fighterProfiles.getTotalPages(),
                fighterProfiles.isFirst(),
                fighterProfiles.isLast());
    }

    public Set<FighterProfileResponse> findAllFighterNearestToLocation(Long fighterId, Long kmRadius) {
        FighterProfile currentFighterProfile = fighterProfileRepository.findById(fighterId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterId));
        BigDecimal radius = BigDecimal.valueOf(kmRadius);
        List<FighterProfileResponse> fighterProfileResponse = fighterProfileRepository.findAll().stream()
                .filter(fighter -> !fighter.getId().equals(fighterId))
                .filter(fighter -> fighter.getLocation().distance(currentFighterProfile.getLocation()).compareTo(radius) <= 0)
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();

        return Set.copyOf(fighterProfileResponse);
    }
}
