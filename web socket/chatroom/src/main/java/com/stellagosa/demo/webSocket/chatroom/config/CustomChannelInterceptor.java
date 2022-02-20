package com.stellagosa.demo.webSocket.chatroom.config;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/7 13:31
 * @Description:
 */
@Component
public class CustomChannelInterceptor implements ChannelInterceptor {

    private final UserOnlineManager userOnlineManager;
    private final WebsocketSessionHolder websocketSessionHolder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomChannelInterceptor(UserOnlineManager userOnlineManager, WebsocketSessionHolder websocketSessionHolder) {
        this.userOnlineManager = userOnlineManager;
        this.websocketSessionHolder = websocketSessionHolder;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null) {
            if (StompCommand.CONNECT.equals(accessor.getCommand())
                    || StompCommand.SEND.equals(accessor.getCommand())) {
                String token = accessor.getFirstNativeHeader("token");
                User user = userOnlineManager.search(token);
                logger.info(user.getUsername() + "正在" + accessor.getCommand().toString() + "...");
                if (user.getUsername() == null) {
                    logger.info(user.getUsername() + accessor.getCommand().toString() + "失败");
                    try {
                        websocketSessionHolder.closeSession(accessor.getSessionId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                accessor.setUser(new UserPrincipal(user.getUsername()));
            }
        }
        return message;
    }
}
