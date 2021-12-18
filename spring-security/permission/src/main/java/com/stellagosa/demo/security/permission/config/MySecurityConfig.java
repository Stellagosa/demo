package com.stellagosa.demo.security.permission.config;

import com.stellagosa.demo.security.permission.config.session.SessionExpiredStrategy;
import com.stellagosa.demo.security.permission.handler.LoginAuthenticationFailureHandler;
import com.stellagosa.demo.security.permission.handler.LoginAuthenticationSuccessHandler;
import com.stellagosa.demo.security.permission.handler.MyAuthenticationAccessDeniedHandler;
import com.stellagosa.demo.security.permission.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginAuthenticationFailureHandler loginFailureHandler;
    @Autowired
    private MyAuthenticationAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private SessionExpiredStrategy sessionExpiredStrategy;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/image/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)//权限不足的
            .and()
            .formLogin()
            .loginPage("/loginPage")
            .loginProcessingUrl("/login")
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler)
            .and()
            .sessionManagement()
            .invalidSessionUrl("/session/invalid")
            .maximumSessions(1)
            .expiredSessionStrategy(sessionExpiredStrategy)
            .and()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(myLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .and()
            .authorizeRequests()
            .antMatchers("/loginPage", "session/invalid").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }
}
