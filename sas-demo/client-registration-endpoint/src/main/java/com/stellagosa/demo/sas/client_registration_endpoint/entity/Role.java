package com.stellagosa.demo.sas.client_registration_endpoint.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Stellagosa
 * @Date: 2022/6/23 10:47
 * @Description:
 */
@Data
public class Role {
    private Long id;
    private String code;
    private String name;
    private Boolean deleted;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
