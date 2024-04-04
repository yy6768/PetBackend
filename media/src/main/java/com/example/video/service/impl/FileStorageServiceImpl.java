package com.example.video.service.impl;

import com.example.video.service.file.FileStorageService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private COSClient cosClient;

    @Value("${cos.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file, FileType fileType) throws IOException{
        String prefix = getPrefixByFileType(fileType);
        String fileName = prefix + generateFileName(file);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
        cosClient.putObject(putObjectRequest);
        return cosClient.getObjectUrl(bucketName, fileName).toString();
    }

    private String getPrefixByFileType(FileType fileType) {
        return switch (fileType) {
            case IMAGE -> "images/";
            case VIDEO -> "videos/";
            case MODEL -> "models/";
        };
    }

    private String generateFileName(MultipartFile file) {
        // 生成文件名逻辑，如使用UUID等
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }
}
