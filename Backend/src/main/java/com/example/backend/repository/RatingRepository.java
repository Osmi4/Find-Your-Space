package com.example.backend.repository;

import com.example.backend.entity.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, String> {
    Optional<Rating> findByRatingId(String ratingId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Rating WHERE rating_id = :ratingId", nativeQuery = true)
    int deleteByRatingId(String ratingId);}
