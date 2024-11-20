package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.entities.user.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage saveMessage(ChatMessage message);

    List<ChatMessage> getMessagesBetweenUsers(String sender, String receiver);
}
