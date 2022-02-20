package com.stellagosa.demo.webSocket.chatroom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/18 12:50
 * @Description:
 */
@Component
public class WebsocketSessionHolder {

    private final Map<String, WebSocketSession> sessions;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    {
        sessions = new HashMap<>();
    }

    public void addSession(String sessionId, WebSocketSession session) {
        synchronized (sessions) {
            sessions.put(sessionId, session);
            logger.info("新建session --> " + sessionId);
        }
    }

    public void closeSession(String sessionId) throws IOException {
        synchronized (sessions) {
            WebSocketSession session = sessions.get(sessionId);
            if (session.isOpen()) {
                session.close(CloseStatus.POLICY_VIOLATION);
            }
            sessions.remove(sessionId);
            logger.info("移除session --> " + sessionId);
        }
    }
}
