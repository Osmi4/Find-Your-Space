package com.example.backend.dtos.Rating;

import com.example.backend.dtos.User.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private String ratingId;
    private double score;
    private String comment;
    private Date dateAdded;
    private String spaceId;
    private String user;
}
