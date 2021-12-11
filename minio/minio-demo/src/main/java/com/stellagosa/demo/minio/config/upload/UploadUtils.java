package com.stellagosa.demo.minio.config.upload;

import com.stellagosa.demo.minio.entity.FileUploadInfo;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class UploadUtils {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public UploadUtils(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    public String generateObjectName(String originalFilename) {
        long currentTimeMillis = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();
        return Long.toHexString(currentTimeMillis) + uuid + originalFilename;
    }

    public FileUploadInfo putObject(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        String objectName = generateObjectName(multipartFile.getOriginalFilename());
        try {
            boolean flag = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!flag) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }
            InputStream stream = multipartFile.getInputStream();
            putObject(minioProperties.getBucketName(), objectName, stream, contentType, multipartFile.getSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getInfo(objectName, multipartFile);
    }

    public FileUploadInfo getInfo(String objectName, MultipartFile multipartFile) {
        FileUploadInfo info = new FileUploadInfo();
        info.setOriginalFileName(multipartFile.getOriginalFilename());
        info.setOriginalFileExtensionName(multipartFile.getContentType());
        info.setBucket_name(minioProperties.getBucketName());
        info.setFileNameInServer(objectName);
        info.setFileSize(multipartFile.getSize());
        info.setFileUrl(getObjectUrl(objectName));
        return info;
    }

    public String getObjectUrl(String objectName) {
        return minioProperties.getEndpoint() + ":" + minioProperties.getPort() + "/" + minioProperties.getBucketName() + "/" + objectName;
    }

    private void putObject(String bucketName, String objectName, InputStream stream, String contentType, long size) {
        try {
            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName)
                            .object(objectName)
                            .stream(stream, size, -1)
                            .contentType(contentType)
                            .build()
            );
            System.out.println(response);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}