package com.stellagosa.demo.h2.hello;

import java.sql.*;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/18 18:10
 * @Description: h2 数据库
 */
public class Demo {

    // 嵌入式连接
    // private static final String JDBC_URL = "jdbc:h2:E:/Tools/DB/H2/h2Data/user";
    // 远程连接
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/E:/Tools/DB/H2/h2Data/user";

    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String DRIVER_CLASS = "org.h2.Driver";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists user_info");
        statement.execute("create table user_info(id INTEGER PRIMARY KEY, name varchar(100), sex varchar(2))");
        statement.executeUpdate("insert into user_info values(1, '刘备', '男')");
        statement.executeUpdate("insert into user_info values(2, '黄忠', '男')");
        statement.executeUpdate("insert into user_info values(3, '孙尚香', '女')");
        statement.executeUpdate("insert into user_info values(4, '貂蝉', '女')");
        statement.executeUpdate("insert into user_info values(5, '曹丕', '男')");

        ResultSet resultSet = statement.executeQuery("select * from user_info");
        while (resultSet.next()) {
            System.out.println("id: " + resultSet.getInt("id") + ", name: " + resultSet.getString("name") + ", 性别: " + resultSet.getString("sex"));
        }
        statement.close();
        connection.close();
    }
}
