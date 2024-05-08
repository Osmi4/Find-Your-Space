package com.example.backend.dtos.Space;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.enums.Availibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceResponse {
    private String spaceId;
    private String spaceName;
    private String spaceLocation;
    private double spaceSize;
    private double spacePrice;
    private String spaceImage;
    private UserResponse owner;
    private Availibility availability;
    private Date dateAdded;
    private Date dateUpdated;
    public Serializable getId() {
        return this.spaceId;
    }
}
