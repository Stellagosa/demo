package com.stellagosa.demo.security.permission.config;

import com.stellagosa.demo.security.permission.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

@Configuration
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = new MyUser();
        user.setUsername(username).setPassword(passwordEncoder.encode("123456"));
        // 只有 user 用户拥有 admin权限，其他全是 test 权限
        if (StringUtils.equalsIgnoreCase(username, "user")) {
            user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        } else {
            user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("test"));
        }

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                user.getAuthorities());
    }
}
