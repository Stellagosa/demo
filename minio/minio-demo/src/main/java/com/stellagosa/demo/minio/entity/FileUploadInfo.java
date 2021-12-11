package com.stellagosa.demo.minio.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileUploadInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 文件原始名称
     */
    private String originalFileName;

    /**
     * 原始文件扩展名
     */
    private String originalFileExtensionName;

    /**
     * 文件存放在服务器的桶名称
     */
    private String bucket_name;

    /**
     * 文件存放在服务器中的名称
     */
    private String fileNameInServer;

    /**
     * 文件类型（1文本，2图片，3音频，4视频）
     */
    private String fileType;

    /**
     * 文件大小（单位：字节）
     */
    private Long fileSize;
    /**
     * 文件 url
     */
    private String fileUrl;

    /**
     * 文件是否删除，0未删除，1已删除
     */
    private String deleted;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人id
     */
    private String createById;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 更新人id
     */
    private String updateById;
}
