package com.example.backend.dtos.Space;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.SpaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceFilter {
    private String spaceName;
    private String spaceLocation;
    private double spaceSizeLowerBound;
    private double spaceSizeUpperBound;
    private double spacePriceLowerBound;
    private double spacePriceUpperBound;
    private SpaceType spaceType;
    private Date StartDate;
    private Date EndDate;
    private String ownerId;
    private Availibility availability;
}
