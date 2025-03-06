package com.fighthub.fighthubapi.fighter_profile;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FighterProfileRepository extends JpaRepository<FighterProfile, Long> {
    Page<FighterProfile> findAllByClubId(Long clubId, Pageable pageable);
}
