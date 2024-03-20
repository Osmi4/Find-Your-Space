package com.example.backend.dtos.Space;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSpaceRequest {
    private String spaceName;
    private String spaceLocation;
    private double spaceSize;
    private double spacePrice;
    private String spaceImage;
    private String availibility;
}
