package com.example.backend.service;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceFilter;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.Space;

import java.util.List;

public interface SpaceService {

    SpaceResponse addSpace( AddSpaceRequest addSpaceRequest);
    SpaceResponse editSpace( EditSpaceRequest editSpaceRequest);

    SpaceResponse deleteSpace(String id);

    List<SpaceResponse> filterSpace(SpaceFilter spaceFilter);

}
