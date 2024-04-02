package com.example.backend.service.impl;

import com.example.backend.dtos.Payment.UpdatePaymentRequest;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingFilter;
import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.dtos.Report.ReportStatus;
import com.example.backend.entity.Rating;
import com.example.backend.entity.Report;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.repository.RatingRepository;
import com.example.backend.repository.ReportRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, SpaceRepository spaceRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RatingResponse addRating(AddRatingRequest addRatingRequest) {
        Rating rating = mapRatingRequestToRating(addRatingRequest);
        Rating savedRating = ratingRepository.save(rating);
        return mapRatingToRatingResponse(savedRating);
    }

    private RatingResponse mapRatingToRatingResponse(Rating rating) {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRatingId(rating.getRatingId());
        ratingResponse.setScore(rating.getScore());
        ratingResponse.setComment(rating.getComment());
        ratingResponse.setDateAdded(rating.getDateAdded());
        ratingResponse.setSpaceId(rating.getSpace().getSpaceId());
        ratingResponse.setUser(rating.getUser().getUserId());
        return ratingResponse;
    }


    @Override
    public RatingResponse getRating(String id) {
        return ratingRepository.findById(id)
                .map(this::mapRatingToRatingResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found"));
    }

    @Override
    public RatingResponse updateRating(UpdatePaymentRequest updatePaymentRequest) {
        return null;
    }

    @Override
    public List<RatingResponse> getRatings(RatingFilter ratingFilter) {
        return null;
    }

    public Rating mapRatingRequestToRating(AddRatingRequest addRatingRequest) {
        Rating rating = new Rating();
        rating.setScore(addRatingRequest.getScore());
        rating.setComment(addRatingRequest.getComment());
        rating.setDateAdded(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        Space space = spaceRepository.findBySpaceId(addRatingRequest.getSpaceId());
        rating.setSpace(space);
        rating.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return rating;
    }
}
