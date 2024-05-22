package com.example.backend.controllers;

import com.example.backend.entity.Image;
import com.example.backend.service.ImageManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/spaces/{spaceId}/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    private final ImageManagementService imageManagementService;

    public ImageController(ImageManagementService imageManagementService) {
        this.imageManagementService = imageManagementService;
    }

    @PostMapping
    public ResponseEntity<Image> uploadImage(@PathVariable String spaceId, @RequestParam("image") MultipartFile file) throws Exception {
        return ResponseEntity.ok(imageManagementService.uploadAndSaveImage(file, spaceId));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String imageId) throws Exception {
        imageManagementService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
