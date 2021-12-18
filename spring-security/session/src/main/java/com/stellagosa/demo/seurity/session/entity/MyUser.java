package com.stellagosa.demo.seurity.session.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MyUser implements Serializable {

    private static final long serialVersionUID = 5294558904946253722L;

    private Integer id;

    private String password;

    private String username;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

}
