package com.stellagosa.demo.security.authentication.config;

import com.stellagosa.demo.security.authentication.handler.MyAuthenticationFailureHandler;
import com.stellagosa.demo.security.authentication.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ellaend
 * @date 2020/11/6 12:08
 * version 1.0
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage("/authentication/require") //指定跳转到登录页面的请求 url
            .loginProcessingUrl("/login") //对应登录页面的 form 表单的 action="/login"
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/authentication/require", "/login.html").permitAll() //跳转登录页面的 url 不要拦截
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }
}
