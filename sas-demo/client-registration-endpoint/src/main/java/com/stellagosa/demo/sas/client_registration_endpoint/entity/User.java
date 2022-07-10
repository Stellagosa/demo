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
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String mobile;
    private String description;
    private Boolean deleted;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
