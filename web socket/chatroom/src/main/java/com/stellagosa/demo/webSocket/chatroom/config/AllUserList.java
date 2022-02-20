package com.stellagosa.demo.webSocket.chatroom.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/8 16:56
 * @Description: 所有用户列表，代替数据库
 */
@Component
public class AllUserList {
    Map<String, User> map = new HashMap<>();

    {
        // 初始化用户列表
        map.put("张三", new User("张三", "123"));
        map.put("李四", new User("李四", "1234"));
        map.put("王五", new User("王五", "12345"));
        map.put("马六", new User("马六", "123456"));
        map.put("Marry", new User("Marry", "654321"));
        map.put("Jack", new User("Jack", "1357"));
    }

    // 根据用户名查找
    public User searchByUsername(String username) {
        return map.getOrDefault(username, new User());
    }

}
