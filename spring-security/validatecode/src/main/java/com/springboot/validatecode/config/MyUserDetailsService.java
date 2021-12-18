package com.springboot.validatecode.config;

import com.springboot.validatecode.entity.MyUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MyUserDetailsService implements UserDetailsService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder().encode("123456"));

        System.out.println("password:" + user.getPassword());

//        public User(String username, String password, boolean enabled,
//        boolean accountNonExpired, boolean credentialsNonExpired,
//        boolean accountNonLocked, Collection<? extends GrantedAuthority > authorities)
        return new User(user.getUsername(), user.getPassword(), user.isEnable(), user.isAccountNonExpired(), user
                .isCredentialIsNonExpired(), user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }
}
