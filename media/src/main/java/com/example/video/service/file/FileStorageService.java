package com.example.video.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileStorageService {
    enum FileType {IMAGE, VIDEO, MODEL}
    String uploadFile(MultipartFile file, FileType fileType) throws IOException;
}
