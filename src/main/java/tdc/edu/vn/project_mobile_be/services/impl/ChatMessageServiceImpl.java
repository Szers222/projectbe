package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.entities.user.ChatMessage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ChatMessageRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ChatMessageService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(java.time.LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesBetweenUsers(String sender, String receiver) {
        return chatMessageRepository.findAll()
                .stream()
                .filter(msg -> (msg.getSender().equals(sender) && msg.getReceiver().equals(receiver)) ||
                        (msg.getSender().equals(receiver) && msg.getReceiver().equals(sender)))
                .sorted(Comparator.comparing(ChatMessage::getTimestamp)) // Sắp xếp tăng dần theo timestamp
                .toList();
    }
    @Override
    public List<ChatMessage> getMessagesForUser(String receiver) {
        return chatMessageRepository.findByReceiver(receiver)
                .stream()
                .collect(Collectors.groupingBy(ChatMessage::getSender)) // Nhóm theo sender
                .values()
                .stream()
                .map(messages -> messages.stream()
                        .min(Comparator.comparing(ChatMessage::getTimestamp)) // Lấy tin nhắn cũ nhất
                        .orElse(null))
                .filter(Objects::nonNull) // Loại bỏ các giá trị null
                .sorted(Comparator.comparing(ChatMessage::getTimestamp)) // Sắp xếp tăng dần theo timestamp
                .toList();
    }


}


