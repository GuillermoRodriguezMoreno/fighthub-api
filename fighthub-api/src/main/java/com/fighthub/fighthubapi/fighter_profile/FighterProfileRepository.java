package com.fighthub.fighthubapi.fighter_profile;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FighterProfileRepository extends JpaRepository<FighterProfile, Long> {
    Page<FighterProfile> findAllByClubId(Long clubId, Pageable pageable);
    List<FighterProfile> findByFirstnameContainingIgnoreCase(String name);

    List<FighterProfile> findByFirstnameContainingIgnoreCaseAndClubIdIsNull(String name);
}
