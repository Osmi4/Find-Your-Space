package com.example.backend.service.impl;

import com.example.backend.dtos.Payment.UpdatePaymentRequest;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingFilter;
import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Override
    public RatingResponse addRating(AddRatingRequest addRatingRequest) {
        return null;
    }

    @Override
    public RatingResponse getRating(String id) {
        return null;
    }

    @Override
    public RatingResponse updateRating(UpdatePaymentRequest updatePaymentRequest) {
        return null;
    }

    @Override
    public List<RatingResponse> getRatings(RatingFilter ratingFilter) {
        return null;
    }
}
