package com.example.backend.controllers;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.service.SpaceService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/space")
public class SpaceController {
    private final SpaceService spaceService;
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    public SpaceResponse addSpace(@RequestBody AddSpaceRequest addSpaceRequest){
        return spaceService.addSpace(addSpaceRequest);
    }

    public SpaceResponse addSpace(@RequestBody EditSpaceRequest editSpaceRequest){
        return spaceService.editSpace(editSpaceRequest);
    }
}
