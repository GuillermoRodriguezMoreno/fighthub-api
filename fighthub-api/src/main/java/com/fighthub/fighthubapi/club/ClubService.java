package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.club.*;
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

    public PageResponse<ClubResponse> findAllClubs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
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
        club.setName(request.name());
        club.setAddress(request.address());
        club.setEmail(request.email());
        club.setDescription(request.description());
        club.setPhone(request.phone());
        club.setOwner(request.owner());

        return clubMapper.toClubResponse(clubRepository.save(club));
    }
    public void deleteClub(Long clubId) {
        clubRepository.deleteById(clubId);
    }
}
