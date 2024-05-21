package com.example.backend.service.impl;

import com.example.backend.entity.Image;
import com.example.backend.entity.Space;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.ImageRepository;
import com.example.backend.repository.ReportRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.service.ImageManagementService;
import com.example.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageManagementServiceImpl implements ImageManagementService {
    private final ImageService imageService;
    private final SpaceRepository spaceRepository;
    private final ImageRepository imageRepository;

    public ImageManagementServiceImpl(ImageService imageService, SpaceRepository spaceRepository, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.spaceRepository = spaceRepository;
        this.imageRepository = imageRepository;
    }


    public Image uploadAndSaveImage(MultipartFile file, String spaceId) throws Exception {
        Optional<Space> space = spaceRepository.findById(spaceId);
        if(space.isEmpty()) {
            throw new ResourceNotFoundException("Space not found" , "spaceId", spaceId);
        }
        File convertedFile = convertMultiPartToFile(file);
        String imageUrl = imageService.uploadImage(convertedFile);
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setSpace(space.get());
        return imageRepository.save(image);
    }

    public void deleteImage(String imageId) throws Exception {
        Image image = imageRepository.findByImageId(imageId);
        if(image == null) {
            throw new ResourceNotFoundException("Image not found" , "imageId", imageId);
        }
        imageService.deleteImage(extractPublicIdFromUrl(image.getImageUrl()));
        imageRepository.delete(image);
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        Path tempDir = Files.createTempDirectory("temp_files");
        File tempFile = tempDir.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String extractPublicIdFromUrl(String url) {
        String[] urlParts = url.split("/");
        String filename = urlParts[urlParts.length - 1];
        String publicId = filename.substring(0, filename.lastIndexOf('.'));
        return publicId;
    }
}
