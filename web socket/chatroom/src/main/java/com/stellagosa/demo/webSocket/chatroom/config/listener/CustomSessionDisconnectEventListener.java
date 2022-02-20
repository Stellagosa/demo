package com.stellagosa.demo.webSocket.chatroom.config.listener;

import com.stellagosa.demo.webSocket.chatroom.config.UserOnlineManager;
import com.stellagosa.demo.webSocket.chatroom.config.WebsocketSessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.security.Principal;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/19 16:29
 * @Description:
 */
@Component
public class CustomSessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    private final UserOnlineManager userOnlineManager;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebsocketSessionHolder websocketSessionHolder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomSessionDisconnectEventListener(UserOnlineManager userOnlineManager, SimpMessagingTemplate simpMessagingTemplate, WebsocketSessionHolder websocketSessionHolder) {
        this.userOnlineManager = userOnlineManager;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.websocketSessionHolder = websocketSessionHolder;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Principal user = event.getUser();
        if (user != null && user.getName() != null && !"".equals(user.getName())) {
            logger.info("用户" + user.getName() + "已下线");
            userOnlineManager.removeUser(user.getName());
            userLogoutPublish(user.getName());
        }
        try {
            websocketSessionHolder.closeSession(event.getSessionId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 单用户下线通知
    public void userLogoutPublish(String username) {
        logger.info("发布用户" + username + "下线消息");
        simpMessagingTemplate.convertAndSend("/topic/userLogout", username);
    }
}
