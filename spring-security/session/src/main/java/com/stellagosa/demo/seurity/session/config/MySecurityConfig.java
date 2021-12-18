package com.stellagosa.demo.seurity.session.config;

import com.stellagosa.demo.seurity.session.Handler.LoginAuthenticationFailureHandler;
import com.stellagosa.demo.seurity.session.Handler.LoginAuthenticationSuccessHandler;
import com.stellagosa.demo.seurity.session.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationSuccessHandler successHandler;
    @Autowired
    private LoginAuthenticationFailureHandler failureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/loginPage")
            .loginProcessingUrl("/login")
            .failureHandler(failureHandler)
            .successHandler(successHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/loginPage", "/code/image", "/session/invalid").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable()
            .sessionManagement() //添加session管理器
            .invalidSessionUrl("/session/invalid") // session 失效后的跳转链接
            .maximumSessions(1)
            .expiredSessionStrategy(sessionExpiredStrategy);
    }
}
