package com.example.backend.repository;

import com.example.backend.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, String> {



    List<Space> findByOwner_UserId(String userId);

    //long deleteBySpaceId(String spaceId);



    Optional<Space> findBySpaceId(String spaceId);

    @Query("""
            select s from Space s
            where s.spacePrice <= ?1
            and s.spacePrice >= ?2
            and s.spaceSize <= ?3
            and s.spaceSize >= ?4
            and (?5 IS NULL OR s.owner.userId = ?5)""")
    List<Space> findSpacesByPriceRangeAndSizeLimitForOwner(double spacePriceUp, double spacePriceLow, double spaceSizeUp, double spaceSizeLow, @Nullable String userId);

    @Transactional
    @Modifying
    @Query("delete from Space s where s.spaceId = ?1")
    int deleteBySpaceId(String spaceId);

}
