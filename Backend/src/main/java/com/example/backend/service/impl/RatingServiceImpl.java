package com.example.backend.service.impl;

import com.example.backend.autoMapper.RatingMapper;
import com.example.backend.dtos.Payment.UpdatePaymentRequest;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingFilter;
import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.dtos.Report.ReportStatus;
import com.example.backend.entity.Rating;
import com.example.backend.entity.Report;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.RatingRepository;
import com.example.backend.repository.ReportRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.RatingService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Space space = spaceRepository.findById(addRatingRequest.getSpaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Space not found", "spaceId", addRatingRequest.getSpaceId()));
        Rating rating = RatingMapper.INSTANCE.RatingRequestToRating(addRatingRequest, space, userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!", "email", SecurityContextHolder.getContext().getAuthentication().getName())));
        Rating savedRating = ratingRepository.save(rating);
        return RatingMapper.INSTANCE.RatingToRatingResponse(savedRating);
    }




    @Override
    public RatingResponse getRating(String id) {
        return ratingRepository.findById(id)
                .map(RatingMapper.INSTANCE::RatingToRatingResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found", "ratingId", id));
    }

    @Override
    @Transactional
    public void deleteRating(String id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isEmpty()) {
            throw new ResourceNotFoundException("Rating not found", "ratingId", id);
        }
        int deleted = ratingRepository.deleteByRatingId(id);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rating not deleted");
        }
    }


    @Override
    public Page<RatingResponse> getRatingsByFilters(RatingFilter ratingFilter, Pageable pageable) {
        return ratingRepository.findRatingsByFilter(ratingFilter.getSpaceId(), ratingFilter.getOwnerId(), pageable)
                .map(RatingMapper.INSTANCE::RatingToRatingResponse);
    }


}
