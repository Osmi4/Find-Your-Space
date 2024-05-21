package com.example.backend.service;

import com.cloudinary.Cloudinary;

import java.io.File;

public interface ImageService {
    String uploadImage(File file) throws Exception;
    void deleteImage(String publicId) throws Exception;
}
