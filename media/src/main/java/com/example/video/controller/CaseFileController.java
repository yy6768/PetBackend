package com.example.video.controller;

import com.example.video.service.file.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class CaseFileController {
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/case/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            assert contentType != null;
            if (contentType.startsWith("video/")) {
                String videoUrl = fileStorageService.uploadFile(file, FileStorageService.FileType.VIDEO);
                return ResponseEntity.ok().body("Video uploaded successfully: " + videoUrl);
            } else if(contentType.startsWith("image/")) {
                String imageUrl = fileStorageService.uploadFile(file, FileStorageService.FileType.IMAGE);
                return ResponseEntity.ok().body("Image uploaded successfully: " + imageUrl);
            } else {
                return ResponseEntity.badRequest().body("Failed to upload file: Error Type");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }
}
