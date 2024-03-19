package com.example.backend.dtos.Space;

import com.example.backend.dtos.User.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditSpaceRequest {
    private String spaceId;
    private String spaceName;
    private String spaceLocation;
    private double spaceSize;
    private double spacePrice;
    private String spaceImage;
    private UserResponse owner;
    private String availibility;
}
