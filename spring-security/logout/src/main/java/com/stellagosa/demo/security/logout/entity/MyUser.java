package com.stellagosa.demo.security.logout.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ellaend
 * @date 2020/11/9 19:48
 * version 1.0
 */
@Data
@Accessors(chain = true)
public class MyUser implements Serializable {
    private static final long serialVersionUID = -2086320453651172868L;

    private String username;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
}
