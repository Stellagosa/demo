package com.stellagosa.demo.security.permission.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@Accessors(chain = true)
public class MyUser implements Serializable {

    private static final long serialVersionUID = 6427038355603447137L;

    private String username;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Collection<? extends GrantedAuthority> authorities;
}
