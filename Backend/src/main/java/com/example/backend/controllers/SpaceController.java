package com.example.backend.controllers;

import com.example.backend.service.SpaceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/space")
public class SpaceController {
    private final SpaceService spaceService;
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }
}
