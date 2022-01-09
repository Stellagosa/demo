package com.stellagosa.demo.webSocket.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Stellagosa
 * @Date: 2022/1/9 17:50
 * @Description:
 */
@Component
public class WebSocketUtils {

    private static final Map<String, WebSocketSession> map = new ConcurrentHashMap<>();

    public WebSocketSession getSession(String key) {
        return map.get(key);
    }

    public void addSession(String key, WebSocketSession session) {
        map.put(key, session);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public Map<String, WebSocketSession> getAll() {
        return map;
    }
}
