package com.example.backend.controllers;

import com.example.backend.service.RatingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    private final RatingService ratingService;
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
}
