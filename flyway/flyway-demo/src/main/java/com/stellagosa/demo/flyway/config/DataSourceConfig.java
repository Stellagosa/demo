package com.stellagosa.demo.flyway.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver_class_name;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximum_pool_size;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private int max_lifetime;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connection_timeout;
    @Value("${spring.datasource.hikari.validation-timeout}")
    private int validation_timeout;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private int idle_timeout;
    @Value("${spring.datasource.hikari.initialization-fail-timeout}")
    private int initialization_fail_timeout;
    @Value("${spring.datasource.hikari.auto-commit}")
    private boolean auto_commit;

    public HikariDataSource instance() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver_class_name);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(minimumIdle);
        config.setMaximumPoolSize(maximum_pool_size);
        config.setMaxLifetime(max_lifetime);
        config.setConnectionTimeout(connection_timeout);
        config.setValidationTimeout(validation_timeout);
        config.setIdleTimeout(idle_timeout);
        config.setInitializationFailTimeout(initialization_fail_timeout);
        config.setAutoCommit(auto_commit);

        return new HikariDataSource(config);
    }

    /**
     * DependsOn，确保数据库已经创建完成，然后创建 datasource
     * @return datasource
     */
    @Bean(name = "dataSource")
    @DependsOn("dataBaseHelper")
    public DataSource dataSource() {
        return instance();
    }
}
