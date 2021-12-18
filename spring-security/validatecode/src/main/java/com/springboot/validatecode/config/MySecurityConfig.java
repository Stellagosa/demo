package com.springboot.validatecode.config;

import com.springboot.validatecode.fliter.ValidateCodeFilter;
import com.springboot.validatecode.handler.MyAuthenticationFailureHandler;
import com.springboot.validatecode.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyAuthenticationSuccessHandler authenticationSuccessHandler;
    private final MyAuthenticationFailureHandler authenticationFailureHandler;
    private final ValidateCodeFilter validateCodeFilter;

    public MySecurityConfig(MyAuthenticationSuccessHandler authenticationSuccessHandler, MyAuthenticationFailureHandler authenticationFailureHandler, ValidateCodeFilter validateCodeFilter) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.validateCodeFilter = validateCodeFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/loginPage") //指定跳转到登录页面的请求 url
            .loginProcessingUrl("/login") //对应登录页面的 form 表单的 action="/login"
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/loginPage", "/code/image").permitAll() //跳转登录页面的 url 不要拦截
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }
}
