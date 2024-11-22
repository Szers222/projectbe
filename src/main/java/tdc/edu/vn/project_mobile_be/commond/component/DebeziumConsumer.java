package tdc.edu.vn.project_mobile_be.commond.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class DebeziumConsumer {
    @Autowired
    private SimpMessagingTemplate template;

    @KafkaListener(topics = "products", groupId = "product-group")
    public void listen(ConsumerRecord<String, String> record) {
        // Xử lý event từ Debezium
        String event = record.value();
        System.out.println("Received event: " + event);
        // Gửi message đến client qua WebSocket
        this.template.convertAndSend("/topic/products", event);
    }
}
