package com.stellagosa.demo.security.authentication.config;

import com.stellagosa.demo.security.authentication.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ellaend
 * @date 2020/11/6 11:56
 * version 1.0
 */

@Configuration
public class MyUserDetailsService implements UserDetailsService {

    // 需要配置
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟一个用户，实际中从数据库中获取
        // username: 任意
        // password: 123456
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.encode("123456"));

        // 加密后的密码
        System.out.println("user:" + user.getPassword());

        // public User(String username, String password, boolean enabled,
        //			boolean accountNonExpired, boolean credentialsNonExpired,
        //			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
        // AuthorityUtils.commaSeparatedStringToAuthorityList("admin") 模拟一个 admin 的权限
        return new User(user.getUsername(), user.getPassword(), user.isEnable(),
                user.isAccountNonExpired(), user.isCredentialIsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
