package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.club.*;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileService;
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
public class ClubService {

    private final ClubRepository clubRepository;
    private final FighterProfileRepository fighterProfileRepository;
    private final ClubMapper clubMapper;

    public Long saveClub(ClubRequest request) {
        Club club = clubMapper.toClub(request);
        return clubRepository.save(club).getId();
    }

    public ClubResponse findClubById(Long clubId) {
        return clubRepository.findById(clubId)
                .map(clubMapper::toClubResponse)
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + clubId));
    }

    public PageResponse<ClubResponse> findAllClubs(Integer page, Integer size, String orderBy) {
        Sort sort = orderBy.equals("name") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Club> clubs = clubRepository.findAll(pageable);
        List<ClubResponse> clubResponse = clubs.stream()
                .map(clubMapper::toClubResponse)
                .toList();
        return new PageResponse<>(
                clubResponse,
                clubs.getNumber(),
                clubs.getSize(),
                clubs.getTotalElements(),
                clubs.getTotalPages(),
                clubs.isFirst(),
                clubs.isLast());
    }
    public ClubResponse updateClub(Long clubId, ClubRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + clubId));
        FighterProfile owner = fighterProfileRepository.findById(request.owner().getId())
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + request.owner().getId()));
        club.setName(request.name());
        club.setAddress(request.address());
        club.setEmail(request.email());
        club.setDescription(request.description());
        club.setPhone(request.phone());
        club.setOwner(owner);

        return clubMapper.toClubResponse(clubRepository.save(club));
    }
    public void deleteClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + clubId));
        club.getEventsOrganized().forEach(event -> event.setOrganizer(null));
        club.getMembers().forEach(fighterProfile -> fighterProfile.setClub(null));
        clubRepository.deleteById(clubId);
    }

    public ClubResponse findClubByOwnerId(Long ownerId) {
        return clubRepository.findByOwnerId(ownerId)
                .map(clubMapper::toClubResponse)
                .orElseThrow(() -> new EntityNotFoundException("club not found with owner id: " + ownerId));
    }

    public PageResponse<ClubResponse> findClubsOrderByMembersSize(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Club> clubs = clubRepository.findAllOrderByMembersSize(pageable);
        List<ClubResponse> clubResponse = clubs.stream()
                .map(clubMapper::toClubResponse)
                .toList();
        return new PageResponse<>(
                clubResponse,
                clubs.getNumber(),
                clubs.getSize(),
                clubs.getTotalElements(),
                clubs.getTotalPages(),
                clubs.isFirst(),
                clubs.isLast());
    }

    public ClubResponse findClubByFighterProfileId(Long fighterProfileId) {
        return clubRepository.findByFighterProfileId(fighterProfileId)
                .map(clubMapper::toClubResponse)
                .orElseThrow(() -> new EntityNotFoundException("club not found with fighterProfile id: " + fighterProfileId));
    }
}
