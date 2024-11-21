package com.fighthub.fighthubapi.style;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StyleRepository extends JpaRepository<Style, Long> {
    @Modifying
    @Query(value = """
    DELETE FROM fighter_profile_styles WHERE styles_id = :styleId;
    UPDATE fight SET style_id = NULL WHERE style_id = :styleId
    """, nativeQuery = true)
    void deleteStyleAssociations(@Param("styleId") Long styleId);
}
