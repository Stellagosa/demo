package com.stellagosa.demo.security.rememberme.config;

import com.stellagosa.demo.security.rememberme.Handler.LoginAuthenticationFailureHandler;
import com.stellagosa.demo.security.rememberme.Handler.LoginAuthenticationSuccessHandler;
import com.stellagosa.demo.security.rememberme.filter.ValidateCodeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    private final ValidateCodeFilter filter;
    private final LoginAuthenticationSuccessHandler successHandler;
    private final LoginAuthenticationFailureHandler failureHandler;

    private final DataSource dataSource;
    private final MyUserDetailsService myUserDetailsService;

    public MySecurityConfig(ValidateCodeFilter filter, LoginAuthenticationSuccessHandler successHandler, LoginAuthenticationFailureHandler failureHandler, DataSource dataSource, MyUserDetailsService myUserDetailsService) {
        this.filter = filter;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.dataSource = dataSource;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .failureHandler(failureHandler)
                .successHandler(successHandler)
            .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1000)
                .userDetailsService(myUserDetailsService)
            .and()
                .authorizeRequests()
                .antMatchers("/loginPage", "/code/image").permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .csrf().disable();
    }
}
