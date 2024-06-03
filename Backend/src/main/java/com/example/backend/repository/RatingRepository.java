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

    @Query("SELECT r FROM Rating r WHERE " +
            "(:spaceId IS NULL OR r.space.spaceId = :spaceId) AND " +
            "(:userId IS NULL OR r.user.userId = :userId)")
    Page<Rating> findRatingsByFilter(
            @Param("spaceId") @Nullable String spaceId,
            @Param("userId") @Nullable String userId,
            Pageable pageable);

//    Double getAverageRatingBySpace(String spaceId);
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.space.spaceId = :spaceId")
    Double getAverageRatingBySpace(@Param("spaceId") String spaceId);
}
