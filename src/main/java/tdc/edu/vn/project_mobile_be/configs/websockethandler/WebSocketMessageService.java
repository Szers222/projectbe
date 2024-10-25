package tdc.edu.vn.project_mobile_be.configs.websockethandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketMessageService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketMessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToClients(String destination, String message) {
        messagingTemplate.convertAndSend(destination, message);
    }
}
