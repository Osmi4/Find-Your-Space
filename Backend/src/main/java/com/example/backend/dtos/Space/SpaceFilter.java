package com.example.backend.dtos.Space;

import com.example.backend.enums.Availibility;
import com.example.backend.enums.SortType;
import com.example.backend.enums.SortVar;
import com.example.backend.enums.SpaceType;
import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero(message = "Lower bound must be positive or zero")
    private double spaceSizeLowerBound = 0.0;

    @PositiveOrZero(message = "Upper bound must be positive or zero")
    private double spaceSizeUpperBound = Double.MAX_VALUE;

    @PositiveOrZero(message = "Lower bound must be positive or zero")
    private double spacePriceLowerBound = 0.0;

    @PositiveOrZero(message = "Upper bound must be positive or zero")
    private double spacePriceUpperBound = Double.MAX_VALUE;

    private SortType type;
    private SortVar variable;
    private SpaceType spaceType;
    private Date startDate;
    private Date endDate;
    private Availibility availability;
}
