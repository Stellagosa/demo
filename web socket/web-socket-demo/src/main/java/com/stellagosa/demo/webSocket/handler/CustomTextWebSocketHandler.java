package com.stellagosa.demo.webSocket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @Author: Stellagosa
 * @Date: 2022/1/9 13:28
 * @Description: 自定义 websocket 连接
 */
@Component
public class CustomTextWebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("和客户端建立连接");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 接受客户端消息
        String payload = message.getPayload();
        logger.info("获取客户端发送的消息：{}", payload);
        String sendMsg = "Hello, now is " + LocalDateTime.now();
        // 像客户端发送消息
        session.sendMessage(new TextMessage(sendMsg.getBytes(StandardCharsets.UTF_8)));
        // 关闭连接
        session.close(CloseStatus.NORMAL);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        logger.info("服务器连接异常：{}", exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        logger.info("断开连接");
    }
}
