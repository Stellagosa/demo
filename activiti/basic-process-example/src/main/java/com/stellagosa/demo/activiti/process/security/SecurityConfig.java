package com.stellagosa.demo.activiti.process.security;

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
                {"tom", "123456", "ROLE_ACTIVITI_USER"},
                {"admin", "123456", "ROLE_ACTIVITI_ADMIN"},
        };

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        for (String[] user : users) {
            userDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    AuthorityUtils.createAuthorityList(user[2])));
        }
        return userDetailsManager;
    }
}
