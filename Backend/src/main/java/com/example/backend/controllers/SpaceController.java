package com.example.backend.controllers;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.service.SpaceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/space")
public class SpaceController {
    private final SpaceService spaceService;
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    public SpaceResponse addSpace(@RequestBody AddSpaceRequest addSpaceRequest){
        return spaceService.addSpace(addSpaceRequest);
    }

    @PutMapping
    public SpaceResponse editSpace(@RequestBody EditSpaceRequest editSpaceRequest){
        return spaceService.editSpace(editSpaceRequest);
    }
}
