package com.example.backend.dtos.Space;

import com.example.backend.enums.Availibility;
import com.example.backend.enums.SpaceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSpaceRequest {
    @NotBlank(message = "Space name is required.")
    private String spaceName;

    @NotBlank(message = "Space location is required.")
    private String spaceLocation;

    @Min(value = 1, message = "Space size must be greater than 0.")
    private double spaceSize;

    @Min(value = 0, message = "Space price must be non-negative.")
    private double spacePrice;

    private String spaceImage;

    private String spaceDescription;

    @NotNull(message = "Space type is required.")
    private SpaceType spaceType;
}
