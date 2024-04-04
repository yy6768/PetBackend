package com.example.video.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Date;

public class COSUtils {

    public static URL generatePresignedUrl(COSClient cosClient, String bucketName, String key, int expirationTimeInSeconds) {
        // 设置URL过期时间
        Date expiration = new Date(System.currentTimeMillis() + expirationTimeInSeconds * 1000L);
        // 生成预签名URL请求对象
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key);
        req.setExpiration(expiration);

        return cosClient.generatePresignedUrl(req);
    }
}