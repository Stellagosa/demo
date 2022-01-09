package com.stellagosa.demo.webSocket.config;

import com.stellagosa.demo.webSocket.handler.CustomTextWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: Stellagosa
 * @Date: 2022/1/9 13:51
 * @Description:
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final CustomTextWebSocketHandler customTextWebSocketHandler;

    public WebSocketConfig(CustomTextWebSocketHandler customTextWebSocketHandler) {
        this.customTextWebSocketHandler = customTextWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customTextWebSocketHandler, "/websocket");
    }
}
