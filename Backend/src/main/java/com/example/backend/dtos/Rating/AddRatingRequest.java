package com.example.backend.dtos.Rating;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRatingRequest {
    private double score;
    private String comment;
    private String spaceId;
}
