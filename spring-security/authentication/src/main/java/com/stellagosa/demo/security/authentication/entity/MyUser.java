package com.stellagosa.demo.security.authentication.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ellaend
 * @date 2020/11/6 11:38
 * version 1.0
 * 参考 {@link org.springframework.security.core.userdetails.UserDetails}
 */

@Data
public class MyUser implements Serializable {

    private static final long serialVersionUID = 2321115551997873716L;

    private String username;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialIsNonExpired = true;
    private boolean enable = true;
}
