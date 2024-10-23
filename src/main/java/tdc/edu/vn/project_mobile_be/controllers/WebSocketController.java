package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @MessageMapping("/topic/productUpdates") // Thay "/topic/productUpdates" bằng endpoint của bạn
    @SendTo("/topic/productUpdates") // Gửi tin nhắn đến tất cả client đã subscribe
    public String handleMessage(String message) {
        return message; // Hoặc xử lý message trước khi gửi lại cho client
    }
    @MessageMapping("/product")
    @SendTo("/topic/product")
    public String handleProduct(String message) {
        System.out.println("Received: " + message);
        return "Product added: " + message;
    }
}
