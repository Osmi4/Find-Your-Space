package com.example.backend.dtos.Rating;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingFilter {
    private String spaceId;
    private String ownerId;
    private String startDate;
    private String endDate;
    private double minScore;
    private double maxScore;
}
