package com.stellagosa.demo.sas.useinfo.service;

import com.stellagosa.demo.sas.useinfo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Stellagosa
 * @Date: 2022/6/23 9:08
 * @Description:
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final DataSourceTransactionManager dataSourceTransactionManager;

    public CustomUserDetailsService(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSourceTransactionManager dataSourceTransactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(queryUserDetailsByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("该用户不存在"));
    }

    private UserDetails queryUserDetailsByUsername(String username) {

        String sql1 = "select u.id,u.username,u.password,u.enabled, u.account_non_expired, u.credentials_non_expired," +
                "u.account_non_locked from user u where u.username = ? and u.deleted = 1";
        String sql2 = "select urr.role_id from user_role_relation urr where urr.user_id = ? and urr.deleted = 1";
        String sql3 = "select r.code from role r where r.id in (:ids) and r.deleted = 1";


        // 定义事务
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 只读事务
        transactionDefinition.setReadOnly(true);
        // 隔离级别
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        // 传播行为
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        RowMapper<User> userRowMapper = (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setEnabled(rs.getBoolean(4));
            user.setAccountNonExpired(rs.getBoolean(5));
            user.setCredentialsNonExpired(rs.getBoolean(6));
            user.setAccountNonLocked(rs.getBoolean(7));
            return user;
        };

        RowMapper<Long> userRoleRelationRowMapper = new SingleColumnRowMapper<>();
        RowMapper<String> roleRowMapper = new SingleColumnRowMapper<>();

        org.springframework.security.core.userdetails.User userDetails = null;
        // 开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            // 查询用户信息
            List<User> userList = jdbcTemplate.query(sql1, userRowMapper, username);
            if (userList.size() != 1) throw new UsernameNotFoundException("该用户不存在");
            User user = userList.get(0);

            // 查询用户角色关联
            List<Long> roleIdList = jdbcTemplate.query(sql2, userRoleRelationRowMapper, user.getId());
            if (roleIdList.size() == 0) throw new UsernameNotFoundException("该用户不存在");

            // 查询用户角色 code
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("ids", roleIdList);
            List<String> roleCodeList = namedParameterJdbcTemplate.query(sql3, param, roleRowMapper);
            if (roleCodeList.size() == 0) throw new UsernameNotFoundException("该用户不存在");

            // 组装结果
            userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(),
                    user.getAccountNonLocked(), AuthorityUtils.createAuthorityList(roleCodeList.toArray(new String[0]))
            );

            // 提交事务
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (UsernameNotFoundException e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
        }
        return userDetails;
    }
}
