package com.stellagosa.demo.security.sms.config;

import com.stellagosa.demo.security.sms.Handler.LoginAuthenticationFailureHandler;
import com.stellagosa.demo.security.sms.Handler.LoginAuthenticationSuccessHandler;
import com.stellagosa.demo.security.sms.filter.SmsCodeFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginAuthenticationSuccessHandler successHandler;
    private final LoginAuthenticationFailureHandler failureHandler;
    private final SmsCodeFilter smsCodeFilter;
    private final SmsAuthenticationConfig smsAuthenticationConfig;

    public MySecurityConfig(LoginAuthenticationSuccessHandler successHandler, LoginAuthenticationFailureHandler failureHandler, SmsCodeFilter smsCodeFilter, SmsAuthenticationConfig smsAuthenticationConfig) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.smsCodeFilter = smsCodeFilter;
        this.smsAuthenticationConfig = smsAuthenticationConfig;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/loginPage")
            .loginProcessingUrl("/login/mobile")
            .failureHandler(failureHandler)
            .successHandler(successHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/loginPage", "/code/sms","/js/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable()
            .apply(smsAuthenticationConfig);
    }
}
