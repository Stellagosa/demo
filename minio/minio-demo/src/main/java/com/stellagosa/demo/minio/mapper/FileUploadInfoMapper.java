package com.stellagosa.demo.minio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stellagosa.demo.minio.entity.FileUploadInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileUploadInfoMapper extends BaseMapper<FileUploadInfo> {
}
