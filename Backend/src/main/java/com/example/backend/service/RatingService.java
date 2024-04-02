package com.example.backend.service;

import com.example.backend.dtos.Payment.UpdatePaymentRequest;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingFilter;
import com.example.backend.dtos.Rating.RatingResponse;

import java.util.List;

public interface RatingService {
    RatingResponse addRating(AddRatingRequest addRatingRequest);

    RatingResponse getRating(String id);

    void deleteRating(String id);

    List<RatingResponse> getRatings(RatingFilter ratingFilter);
}
