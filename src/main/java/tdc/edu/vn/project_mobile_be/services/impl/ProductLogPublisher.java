package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.configs.rabbitmq.RabbitMQConfig;

@Service
public class ProductLogPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendProductLog(String logMessage) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                logMessage
        );
    }
}

