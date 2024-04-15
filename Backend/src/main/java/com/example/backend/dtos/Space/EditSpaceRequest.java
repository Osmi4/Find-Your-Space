package com.example.backend.dtos.Space;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.enums.Availibility;
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
    private String spaceName;

    private String spaceLocation;

    private double spaceSize;

    private double spacePrice;

    private String spaceDescription;
}
