package com.stellagosa.demo.flyway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class DataBaseHelper {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.datasource.driver-class-name}")
    private String driver_class_name;
    @Value("${database.connection.target}")
    private String databaseName;
    @Value("${database.connection.url}")
    private String databaseUrl;
    @Value("${database.connection.params}")
    private String params;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void init() {
        try {
            Class.forName(driver_class_name);
            Connection connection = DriverManager.getConnection(databaseUrl + "?" + params, username, password);
            // 使用 `，有些数据库名字使用中划线会报错，使用 ` 包起来
            String sql = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            LOGGER.info("数据库创建完成！");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.info("数据库创建失败！");
            e.printStackTrace();
        }
    }
}
