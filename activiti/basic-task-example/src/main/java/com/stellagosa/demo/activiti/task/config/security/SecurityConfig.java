package com.stellagosa.demo.activiti.task.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/21 21:46
 * @Description:
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        String[][] users = {
                {"tom", "123456", "ROLE_ACTIVITI_USER", "GROUP_ACTIVITITEAM"},
                {"jacky", "123456", "ROLE_ACTIVITI_USER", "GROUP_ACTIVITITEAM"},
                {"rose", "123456", "ROLE_ACTIVITI_USER", "GROUP_ACTIVITITEAM"},
                {"other", "123456", "ROLE_ACTIVITI_USER", "GROUP_OTHERTEAM"},
                {"admin", "123456", "ROLE_ACTIVITI_ADMIN", "GROUP_ACTIVITITEAM"},
        };

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        for (String[] user : users) {
            userDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    AuthorityUtils.createAuthorityList(user[2], user[3])));
        }
        return userDetailsManager;
    }
}
