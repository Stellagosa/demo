package com.stellagosa.demo.security.authentication.security.hello.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author ellaend
 * @date 2020/11/6 11:05
 * version 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() //表单登录
//        http.httpBasic() //切换 Http Basic方式
            .and()
            .authorizeRequests() //授权配置
            .anyRequest() //所有请求
            .authenticated(); //认证
    }
}
