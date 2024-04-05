package com.example.media.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


public interface FileStorageService {
    Map<String, String> uploadFile(MultipartFile file);
}
