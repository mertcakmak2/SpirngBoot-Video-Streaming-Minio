package com.medium.githubactions.adapter;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioAdapter {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    String defaultBucketName;

    @Autowired
    public MinioAdapter(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public InputStream getFileAsInputStream(String objectName) {
        try {
            InputStream file = minioClient.getObject(defaultBucketName,objectName);
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getFileLength(String objectName) {
        try {
            InputStream file = minioClient.getObject(defaultBucketName,objectName);
            return file.readAllBytes().length;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
