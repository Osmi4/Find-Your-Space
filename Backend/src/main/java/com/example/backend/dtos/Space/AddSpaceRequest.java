package com.example.backend.dtos.Space;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddSpaceRequest {
    private String spaceName;
    private String spaceLocation;
    private double spaceSize;
    private double spacePrice;
    private String spaceImage;
    private String availibility;
}
