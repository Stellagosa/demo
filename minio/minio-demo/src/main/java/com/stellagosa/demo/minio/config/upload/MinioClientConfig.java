package com.stellagosa.demo.minio.config.upload;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {

    private final MinioProperties minioProperties;

    public MinioClientConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    // secure=true 时使用 https
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint(), minioProperties.getPort(), false)
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

}
