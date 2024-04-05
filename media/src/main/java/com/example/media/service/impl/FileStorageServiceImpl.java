package com.example.media.service.impl;

import com.example.media.service.file.FileStorageService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private COSClient cosClient;

    @Value("${cos.bucketName}")
    private String bucketName;

    public Map<String ,String> uploadFile(MultipartFile file){
        Map<String, String> results = new HashMap<>();
        // 1.Get url prefix
        String prefix = getPrefixByFileType(file.getContentType(), file.getOriginalFilename());
        if (prefix.isEmpty()) {
            results.put("error_message", "File Type is empty");
            return results;
        }
        // 2. Relative file name
        String fileName = prefix + generateFileName(file);
        // 3. Use Cos API to store the file
        PutObjectRequest putObjectRequest;
        try {
            putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
            cosClient.putObject(putObjectRequest);
            String url = cosClient.getObjectUrl(bucketName, fileName).toString();
            results.put("url", url);
            results.put("error_message", "success");
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getPrefixByFileType(String fileType, String originalFilename) {
        if (fileType == null || fileType.isEmpty()) return "";
        if (fileType.startsWith("video"))
            return "video/";
        if (fileType.startsWith("image"))
            return "image/";
        if (originalFilename != null) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            return switch (extension) {
                case "3ds" -> "models/3ds/";
                case "obj" -> "models/obj/";
                // 可以根据需要添加更多的文件类型支持
                default -> "";
            };
        }
        return "";
    }

    private String generateFileName(MultipartFile file) {
        // 生成文件名逻辑，如使用UUID等
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}
