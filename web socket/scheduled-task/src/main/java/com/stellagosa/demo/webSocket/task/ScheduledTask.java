package com.stellagosa.demo.webSocket.task;

import com.stellagosa.demo.webSocket.utils.WebSocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @Author: Stellagosa
 * @Date: 2022/1/9 18:07
 * @Description:
 */
@Component
public class ScheduledTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WebSocketUtils webSocketUtils;

    public ScheduledTask(WebSocketUtils webSocketUtils) {
        this.webSocketUtils = webSocketUtils;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void task() throws IOException {
        Map<String, WebSocketSession> allSession = webSocketUtils.getAll();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (WebSocketSession session : allSession.values()) {
            logger.info("向连接{}发送当前时间: {}", session.getId(), date);
            session.sendMessage(new TextMessage(date.getBytes(StandardCharsets.UTF_8)));
        }
    }
}
