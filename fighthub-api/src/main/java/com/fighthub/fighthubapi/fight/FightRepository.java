package com.fighthub.fighthubapi.fight;


import org.springframework.data.jpa.repository.JpaRepository;

public interface FightRepository extends JpaRepository<Fight, Long> {
}
