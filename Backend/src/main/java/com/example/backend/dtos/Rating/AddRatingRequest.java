package com.example.backend.dtos.Rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddRatingRequest {
    private double score;
    private String comment;
    private String spaceId;
}
