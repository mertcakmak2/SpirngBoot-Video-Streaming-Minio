package com.medium.githubactions.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.access.name}")
    String accessKey;
    @Value("${minio.access.secret}")
    String accessSecret;
    @Value("${minio.url}")
    String minioUrl;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient client = new MinioClient(minioUrl,accessKey,accessSecret);
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
