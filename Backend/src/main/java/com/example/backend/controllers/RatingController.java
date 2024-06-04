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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/filter")
    public ResponseEntity<Page<RatingResponse>>getRatingsByFilter(@RequestBody RatingFilter ratingFilter, @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,  @RequestParam(defaultValue = "dateAdded") String sortField,
                                                                  @RequestParam(defaultValue = "DESC") String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(ratingService.getRatingsByFilters(ratingFilter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRating(@PathVariable String id) {
        return ResponseEntity.ok(ratingService.getRating(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable String id) throws AccessDeniedException {
        ratingService.deleteRating(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<Double> getAverageRatingBySpace(@PathVariable String spaceId) {
        return ResponseEntity.ok(ratingService.getAverageRatingBySpace(spaceId));
    }

}
