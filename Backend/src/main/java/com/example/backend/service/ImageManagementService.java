package com.example.backend.service;

import com.example.backend.entity.Image;
import com.example.backend.entity.Space;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManagementService {
    Image uploadAndSaveImage(MultipartFile file, String spaceId) throws Exception;
    void deleteImage(String imageId) throws Exception;

    String getImage(String spaceId);
}
