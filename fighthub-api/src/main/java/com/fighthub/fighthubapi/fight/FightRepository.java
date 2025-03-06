package com.fighthub.fighthubapi.fight;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FightRepository extends JpaRepository<Fight, Long> {

    @Query("SELECT f FROM Fight f WHERE f.blueCornerFighter.id = :fighterId OR f.redCornerFighter.id = :fighterId")
    Page<Fight> findAllByBlueCornerFighterIdOrRedCornerFighterId(Long fighterId, Pageable pageable);

    Page<Fight> findAllByEventId(Long eventId, Pageable pageable);

    @Query("SELECT f FROM Fight f ORDER BY CASE WHEN f.likes IS NULL THEN 1 ELSE 0 END, f.likes DESC")
    Page<Fight> findAllPopularFights(Pageable pageable);


}
