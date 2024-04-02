package com.example.backend.repository;

import com.example.backend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, String> {
    Rating findByRatingId(String ratingId);
}
