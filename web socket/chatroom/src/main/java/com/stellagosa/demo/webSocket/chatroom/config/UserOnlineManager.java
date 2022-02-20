package com.stellagosa.demo.webSocket.chatroom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/8 16:50
 * @Description: 在线用户管理
 */
@Component
public class UserOnlineManager {

    // 存储在线用户
    Map<String, User> map = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 上线，返回生成的 token
    public String addUser(User user) {
        String token = this.search(user);
        if (!token.equals("")) return token;
        token = UUID.randomUUID().toString();
        map.put(token, user);
        logger.info("用户" + user.getUsername() + "正在登录");
        logger.info("用户" + user.getUsername() + "的token:" + token);
        return token;
    }

    // 下线
    public void removeUser(String username) {
        for (Map.Entry<String, User> entry : map.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                map.remove(entry.getKey());
            }
        }
        logger.info("用户" + username + "下线");
    }

    // 根据 token 查找用户
    public User search(String token) {
        return map.getOrDefault(token, new User());
    }

    // 根据用户查找 token
    public String search(User user) {
        for (Map.Entry<String, User> entry : map.entrySet()) {
            if (entry.getValue().equals(user)) return entry.getKey();
        }
        return "";
    }

    public List<User> getAllUser() {
        return new ArrayList<>(map.values());
    }
}
