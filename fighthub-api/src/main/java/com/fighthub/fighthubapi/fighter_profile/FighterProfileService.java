package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.common.PageResponse;
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

    public PageResponse<FighterProfileResponse> findAllFighterProfiles(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("weight").descending());
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
        fighterProfile.setWeight(request.weight());
        fighterProfile.setHeight(request.height());
        fighterProfile.setGender(request.gender());
        fighterProfile.setBiography(request.biography());
        fighterProfile.setUser(request.user());
        fighterProfile.setStyles(request.styles());
        fighterProfile.setCategory(request.category());
        fighterProfile.setClub(request.club());

        return fighterProfileMapper.toFighterProfileResponse(fighterProfileRepository.save(fighterProfile));
    }
    public void deleteFighterProfile(Long fighterProfileId) {
        fighterProfileRepository.deleteById(fighterProfileId);
    }
}
