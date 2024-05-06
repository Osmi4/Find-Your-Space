package com.example.backend.controllers;

import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingFilter;
import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.service.RatingService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    private final RatingService ratingService;
    //uwzglednic ich sortowanie po score
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    //limit ratingow per space
    @PostMapping("/")
    public ResponseEntity<RatingResponse> addRating(@Valid @RequestBody AddRatingRequest addRatingRequest) {
        return ResponseEntity.ok(ratingService.addRating(addRatingRequest));
    }

    @GetMapping("/")
    public ResponseEntity<Page<RatingResponse>>getRatingsByFilter(@RequestBody RatingFilter ratingFilter, @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ratingService.getRatingsByFilters(ratingFilter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRating(@PathVariable String id) {
        return ResponseEntity.ok(ratingService.getRating(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable String id) {
        ratingService.deleteRating(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    //dodac zebny do space Response byl dodany serdni rating

}
