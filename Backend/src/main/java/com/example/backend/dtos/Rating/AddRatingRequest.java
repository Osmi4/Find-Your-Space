package com.example.backend.dtos.Rating;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "comment is required")
    private String comment;
    @NotBlank(message = "spaceId is required")
    private String spaceId;
}
