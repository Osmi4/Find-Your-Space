package com.example.backend.dtos.Rating;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingResponse {
    private String ratingId;
    private double score;
    private String comment;
    private String raterId;
    private String dateAdded;
    private String spaceId;
    private String startDate;
    private String endDate;

}
