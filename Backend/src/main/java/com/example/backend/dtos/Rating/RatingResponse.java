package com.example.backend.dtos.Rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
