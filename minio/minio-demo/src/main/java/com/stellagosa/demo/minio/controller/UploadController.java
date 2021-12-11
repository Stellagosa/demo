package com.stellagosa.demo.minio.controller;

import com.stellagosa.demo.minio.result.CommonResult;
import com.stellagosa.demo.minio.result.ResultTool;
import com.stellagosa.demo.minio.service.IUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    private final IUploadService uploadService;

    public UploadController(IUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    public CommonResult fileUpload(MultipartFile file) {
        uploadService.upload(file);
        return ResultTool.success();
    }

}