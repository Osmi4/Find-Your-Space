package com.example.backend.dtos.Rating;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRatingRequest {
    private String ratingId;
    private double score;
    private String comment;
    private String spaceId;
}
