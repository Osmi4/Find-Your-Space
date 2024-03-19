package com.example.backend.service;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.Space;

public interface SpaceService {

    SpaceResponse addSpace( AddSpaceRequest addSpaceRequest);
    SpaceResponse editSpace( EditSpaceRequest editSpaceRequest);

    SpaceResponse deleteSpace(String id);

}
