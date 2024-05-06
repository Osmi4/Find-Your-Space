package com.example.backend.repository;

import com.example.backend.entity.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, String> {
    Optional<Rating> findByRatingId(String ratingId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Rating WHERE rating_id = :ratingId", nativeQuery = true)
    int deleteByRatingId(String ratingId);

    @Query("select r from Rating r where r.space.spaceId = ?1 and r.space.owner.userId = ?2")
    Page<Rating> findRatingsByFilter(@Nullable String spaceId, @Nullable String userId, Pageable pageable);
//    @Query("SELECT r FROM Rating r WHERE " +
//            "(:spaceId IS NULL OR r.space.spaceId = :spaceId) AND " +
//            "(:ownerId IS NULL OR r.user.userId = :ownerId)"
//    )
//    Page<Rating> findRatingsByFilter(
//            @Param("spaceId") @Nullable String spaceId,
//            @Param("ownerId")  @Nullable String ownerId
//    );

}
