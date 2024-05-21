package com.example.backend.dtos.Space;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.enums.Availibility;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditSpaceRequest {
    @Nullable
    private String spaceName;

    @Nullable
    private String spaceLocation;

    @Min(value = 1, message = "Space size must be greater than 0.")
    private double spaceSize;

    @Min(value = 0, message = "Space price must be non-negative.")
    private double spacePrice;

    @Nullable
    private String spaceDescription;
}
