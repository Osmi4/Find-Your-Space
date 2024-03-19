package com.example.backend.service;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;

public interface SpaceService {

    SpaceResponse addSpace( AddSpaceRequest addSpaceRequest);
    SpaceResponse editSpace( EditSpaceRequest editSpaceRequest);

}
