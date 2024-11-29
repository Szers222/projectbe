package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tdc.edu.vn.project_mobile_be.entities.user.ChatMessage;
import tdc.edu.vn.project_mobile_be.interfaces.service.ChatMessageService;

@Controller
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;

    public ChatWebSocketController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleTextMessage(ChatMessage message) {
        // Lưu tin nhắn vào database
        return chatMessageService.saveMessage(message);
    }
}