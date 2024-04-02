package com.example.backend.dtos.Rating;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingFilter {
    @Nullable
    private String spaceId;
    @Nullable
    private String ownerId;
//    @Nullable
//    private String startDate;
//    @Nullable
//    private Date endDate;
    @Nullable
    private double minScore;
    @Nullable
    private double maxScore;
}
