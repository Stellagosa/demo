package com.stellagosa.demo.minio.service.impl;

import com.stellagosa.demo.minio.config.upload.UploadUtils;
import com.stellagosa.demo.minio.entity.FileUploadInfo;
import com.stellagosa.demo.minio.service.IFileUploadInfoService;
import com.stellagosa.demo.minio.service.IUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements IUploadService {

    private final UploadUtils uploadUtils;
    private final IFileUploadInfoService fileUploadInfoService;

    public UploadServiceImpl(UploadUtils uploadUtils, IFileUploadInfoService fileUploadInfoService) {
        this.uploadUtils = uploadUtils;
        this.fileUploadInfoService = fileUploadInfoService;
    }

    @Override
    public void upload(MultipartFile file) {
        FileUploadInfo info = uploadUtils.putObject(file);
        fileUploadInfoService.save(info);
    }
}
