package com.example.backend.dtos.Rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingFilter {
    private String spaceId;
    private String ownerId;
    private String startDate;
    private String endDate;
    private double minScore;
    private double maxScore;
}
