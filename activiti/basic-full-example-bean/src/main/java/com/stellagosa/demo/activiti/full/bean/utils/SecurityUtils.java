package com.stellagosa.demo.activiti.full.bean.utils;

import org.activiti.engine.impl.identity.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/21 21:47
 * @Description:
 */
@Component
public class SecurityUtils {

    private final UserDetailsService userDetailsService;

    public SecurityUtils(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void loginBy(String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new IllegalStateException("用户不存在");
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()));
        Authentication.setAuthenticatedUserId(username);
    }
}
