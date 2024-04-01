package com.example.backend.service;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceFilter;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.Space;
import com.example.backend.enums.Availibility;

import java.util.List;

public interface SpaceService {

    SpaceResponse addSpace( AddSpaceRequest addSpaceRequest);
    SpaceResponse editSpace( EditSpaceRequest editSpaceRequest , String spaceId);

    SpaceResponse deleteSpace(String id);

    List<SpaceResponse> searchSpaces(SpaceFilter filter);

    SpaceResponse getSpace(String id);

    SpaceResponse changeAvailability(String spaceId, Availibility availability);

    List<SpaceResponse> getMySpaces();

    List<SpaceResponse> getAllSpaces();
}
