package tdc.edu.vn.project_mobile_be.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.Notification;

@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    public RedisMessageSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
        try {
            String channelName = new String(message.getChannel());
            String messageBody = new String(message.getBody());

            // Convert Redis message to Notification object
            ObjectMapper mapper = new ObjectMapper();
            Notification notification = mapper.readValue(messageBody, Notification.class);

            // Broadcast to all subscribed WebSocket clients
            messagingTemplate.convertAndSend("/topic/notifications", notification);

            log.info("Broadcasted notification from Redis channel {}: {}", channelName, notification);
        } catch (Exception e) {
            log.error("Error processing Redis message", e);
        }
    }
}
