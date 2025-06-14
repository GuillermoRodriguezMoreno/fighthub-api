package com.fighthub.fighthubapi.fighter_profile;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FighterProfileRepository extends JpaRepository<FighterProfile, Long> {
    Page<FighterProfile> findAllByClubId(Long clubId, Pageable pageable);
    List<FighterProfile> findByFirstnameContainingIgnoreCase(String name);

    List<FighterProfile> findByFirstnameContainingIgnoreCaseAndClubIdIsNull(String name);
    @Query("""
        SELECT DISTINCT m
        FROM FighterProfile o
        JOIN o.clubsOwned c
        JOIN c.members       m
        WHERE o.id = :ownerId
    """)
    List<FighterProfile> findMembersOfOwnedClubs(@Param("ownerId") Long ownerId);
}
