package com.fighthub.fighthubapi.club;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("SELECT c FROM Club c LEFT JOIN c.members m GROUP BY c ORDER BY COUNT(m) DESC")
    Page<Club> findAllOrderByMembersSize(Pageable pageable);
    Optional<Club> findByOwnerId(Long ownerId);

    @Query("SELECT c FROM Club c JOIN c.members m WHERE m.id = :fighterProfileId")
    Optional<Club> findByFighterProfileId(Long fighterProfileId);
}
