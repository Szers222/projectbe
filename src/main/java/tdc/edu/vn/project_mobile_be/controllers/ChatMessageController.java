package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdc.edu.vn.project_mobile_be.entities.user.ChatMessage;
import tdc.edu.vn.project_mobile_be.interfaces.service.ChatMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    /**
     * Lấy danh sách tin nhắn giữa hai người.
     *
     * @param sender   Người gửi
     * @param receiver Người nhận
     * @return Danh sách tin nhắn
     */
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessagesBetweenUsers(
            @RequestParam String sender,
            @RequestParam String receiver) {
        List<ChatMessage> messages = chatMessageService.getMessagesBetweenUsers(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/received")
    public ResponseEntity<List<ChatMessage>> getMessagesForUser(@RequestParam String receiver) {
        List<ChatMessage> messages = chatMessageService.getMessagesForUser(receiver);
        return ResponseEntity.ok(messages);
    }
}



