package com.stellagosa.demo.webSocket.chatroom.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/19 14:48
 * @Description:
 */
@Component
public class CustomSessionConnectEventListener implements ApplicationListener<SessionConnectedEvent> {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomSessionConnectEventListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        Principal user = event.getUser();
        if (user != null && user.getName() != null && !user.getName().equals("")) {
            userLoginPublish(user.getName());
        }
        logger.info("用户" + user.getName() + "已连接");
    }

    // 单用户上线通知
    public void userLoginPublish(String username) {
        logger.info("发布用户" + username + "登录消息");
        simpMessagingTemplate.convertAndSend("/topic/userLogin", username);
    }
}
