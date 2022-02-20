package com.stellagosa.demo.webSocket.chatroom.controller;

import com.stellagosa.demo.webSocket.chatroom.config.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @Author: Stellagosa
 * @Date: 2022/1/13 16:49
 * @Description:
 */
@Controller
public class ChatroomController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ChatroomController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send")
    public void chatroom(String str, Principal principal) {
        CustomMessage message = new CustomMessage();
        message.setFrom(principal.getName());
        message.setMsg(str);
        logger.info(principal.getName() + "-->chatroom:" + str);
        simpMessagingTemplate.convertAndSend("/topic/chatroom", message);
    }
}