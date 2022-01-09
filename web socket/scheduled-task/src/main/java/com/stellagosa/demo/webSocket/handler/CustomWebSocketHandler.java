package com.stellagosa.demo.webSocket.handler;

import com.stellagosa.demo.webSocket.utils.WebSocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author: Stellagosa
 * @Date: 2022/1/9 17:48
 * @Description:
 */
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WebSocketUtils webSocketUtils;

    public CustomWebSocketHandler(WebSocketUtils webSocketUtils) {
        this.webSocketUtils = webSocketUtils;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketUtils.addSession(session.getId(), session);
        logger.info("连接已建立，id: {}", session.getId());
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketUtils.remove(session.getId());
        logger.info("连接已销毁，id: {}", session.getId());
        super.afterConnectionClosed(session, status);
    }
}
