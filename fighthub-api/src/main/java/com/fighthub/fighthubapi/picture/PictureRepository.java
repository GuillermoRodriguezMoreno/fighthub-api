package com.fighthub.fighthubapi.picture;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findAllByFighterProfileId(Long fighterProfileId);
}
