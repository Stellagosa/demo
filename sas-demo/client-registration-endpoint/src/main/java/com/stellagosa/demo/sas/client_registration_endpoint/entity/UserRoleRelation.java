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
public class UserRoleRelation {
    private Long id;
    private Long userId;
    private Long roleId;
    private Boolean deleted;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
