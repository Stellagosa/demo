package com.stellagosa.demo.minio.config.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio.config")
public class MinioProperties {

    private String endpoint;
    private Integer port;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
