package com.fighthub.fighthubapi.club;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.event.Event;
import com.fighthub.fighthubapi.event.EventResponse;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import com.fighthub.fighthubapi.picture.SupabaseStorageService;
import com.fighthub.fighthubapi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final FighterProfileRepository fighterProfileRepository;
    private final UserRepository userRepository;
    private final SupabaseStorageService supabaseStorageService;
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

    public void addFighterProfileToClub(Long clubId, Long fighterProfileId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + clubId));
        FighterProfile fighterProfile = fighterProfileRepository.findById(fighterProfileId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterProfileId));
        if (fighterProfile.getClub() != null) {
            throw new IllegalStateException("Fighter profile already belongs to a club");
        }
        fighterProfile.setClub(club);
        fighterProfileRepository.save(fighterProfile);
    }

    public void deleteClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + clubId));
        club.getEventsOrganized().forEach(event -> event.setOrganizer(null));
        club.getMembers().forEach(fighterProfile -> fighterProfile.setClub(null));
        clubRepository.deleteById(clubId);
    }

    public List<ClubResponse> findClubsByOwnerId(String ownerEmail) {
        Long ownerId = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with email: " + ownerEmail))
                .getId();
        return clubRepository.findAllByOwnerId(ownerId).stream()
                .map(clubMapper::toClubResponse)
                .collect(Collectors.toList());
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

    public ClubResponse uploadProfilePicture(Long clubId, MultipartFile file) throws IOException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("club not found with id: " + clubId));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String pictureUrl = supabaseStorageService.upload(file, "clubs").block();
        club.setProfilePicture(pictureUrl);

        return clubMapper.toClubResponse(clubRepository.save(club));
    }
}
