package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.club.ClubRepository;
import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.fight.FightRepository;
import com.fighthub.fighthubapi.user.User;
import com.fighthub.fighthubapi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FighterProfileService {

    private final FighterProfileRepository fighterProfileRepository;
    private final FighterProfileMapper fighterProfileMapper;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final FightRepository fightRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
        Sort sort = orderBy.equals("firstname") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
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
        fighterProfile.setStyles(request.styles());
        fighterProfile.setCategory(request.category());
        fighterProfile.setPictures(request.pictures());
        Optional.ofNullable(request.club()).ifPresent(fighterProfile::setClub);
        Optional.ofNullable(request.location()).ifPresent(fighterProfile::setLocation);
        Optional.ofNullable(request.pictures()).ifPresent(fighterProfile::setPictures);


        return fighterProfileMapper.toFighterProfileResponse(fighterProfileRepository.save(fighterProfile));
    }

    public void deleteFighterProfile(Long fighterProfileId) {
        FighterProfile fighterProfile = fighterProfileRepository.findById(fighterProfileId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterProfileId));
        fighterProfile.getClubsOwned().forEach(club -> {
            club.setOwner(null);
            clubRepository.save(club);
        });
        fighterProfile.getBlueCornerFights().forEach(fight -> {
            fight.setBlueCornerFighter(null);
            if (fight.getWinner() != null && fight.getWinner().getId().equals(fighterProfile.getId())) {
                fight.setWinner(null);
            }
            fightRepository.save(fight);
        });
        fighterProfile.getRedCornerFights().forEach(fight -> {
            fight.setRedCornerFighter(null);
            if (fight.getWinner() != null && fight.getWinner().getId().equals(fighterProfile.getId())) {
                fight.setWinner(null);
            }
            fightRepository.save(fight);
        });
        User user = userRepository.findById(fighterProfile.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("user not found with id: " + fighterProfile.getUser().getId()));

        fighterProfile.setUser(null);
        user.setFighterProfile(null);

        userRepository.save(user);
        fighterProfileRepository.deleteById(fighterProfileId);
    }

    public FighterProfileResponse unsubscribeFromClub(Long fighterProfileId, Long clubId) {
        FighterProfile fighterProfile = fighterProfileRepository.findById(fighterProfileId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterProfileId));
        if (fighterProfile.getClub() != null && fighterProfile.getClub().getId().equals(clubId)) {
            fighterProfile.setClub(null);
            return fighterProfileMapper.toFighterProfileResponse(fighterProfileRepository.save(fighterProfile));
        } else {
            throw new IllegalStateException("Fighter is not subscribed to this club");
        }
    }

    public List<FighterProfileResponse> findByName(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String searchQuery = query.toLowerCase();
        List<FighterProfile> fighterProfiles = fighterProfileRepository.findByFirstnameContainingIgnoreCase(searchQuery);
        List<FighterProfileResponse> fighterProfileResponses = fighterProfiles.stream()
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();
        return fighterProfileResponses;
    }

    public List<FighterProfileResponse> findByNameAndClubIsNull(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String searchQuery = query.toLowerCase();
        List<FighterProfile> fighterProfiles = fighterProfileRepository.findByFirstnameContainingIgnoreCaseAndClubIdIsNull(searchQuery);
        List<FighterProfileResponse> fighterProfileResponses = fighterProfiles.stream()
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();
        return fighterProfileResponses;
    }

    public List<FighterProfileResponse> findMembersOfOwnedClubs(Long ownerId) {
        List<FighterProfile> fighterProfiles = fighterProfileRepository.findMembersOfOwnedClubs(ownerId);
        return fighterProfiles.stream()
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();
    }

    public PageResponse<FighterProfileResponse> findAllFighterProfilesByClubId(Long clubId, Integer page, Integer size, String orderBy) {
        Sort sort = orderBy.equals("firstname") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
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
                .sorted(Comparator.comparing(fighter -> fighter.getLocation().distance(currentFighterProfile.getLocation())))
                .map(fighterProfileMapper::toFighterProfileResponse)
                .toList();

        return Set.copyOf(fighterProfileResponse);
    }

    public String uploadProfilePicture(Long profileId, MultipartFile file) throws IOException {
        FighterProfile fighterProfile = fighterProfileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + profileId));

        Path uploadPathDestination = Paths.get(uploadPath);

        if (!Files.exists(uploadPathDestination)) {
            Files.createDirectories(uploadPathDestination);
        }

        String fileName = fighterProfile.getId() + "_profile_picture_" + file.getOriginalFilename();
        Path filePath = uploadPathDestination.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        fighterProfile.setProfilePicture(fileName);

        fighterProfileRepository.save(fighterProfile);

        return filePath.toString();
    }
}
