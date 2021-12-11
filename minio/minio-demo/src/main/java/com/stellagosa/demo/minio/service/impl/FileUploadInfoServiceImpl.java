package com.stellagosa.demo.minio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stellagosa.demo.minio.entity.FileUploadInfo;
import com.stellagosa.demo.minio.mapper.FileUploadInfoMapper;
import com.stellagosa.demo.minio.service.IFileUploadInfoService;
import org.springframework.stereotype.Service;

@Service
public class FileUploadInfoServiceImpl extends ServiceImpl<FileUploadInfoMapper, FileUploadInfo> implements IFileUploadInfoService {

}