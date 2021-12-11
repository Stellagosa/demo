package com.stellagosa.demo.minio.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

    void upload(MultipartFile file);

}
