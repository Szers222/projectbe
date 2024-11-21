package tdc.edu.vn.project_mobile_be.commond;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import tdc.edu.vn.project_mobile_be.configs.rabbitmq.RabbitMQConfig;

@Component
public class ProductLogListener {

    @Autowired
    private SimpMessagingTemplate template;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveProductLog(String logMessage) {
        // Gửi thông báo đến WebSocket
        template.convertAndSend("/topic/product-updates", logMessage);
    }
}
