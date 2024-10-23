package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.Notification;

@Service
@Slf4j
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String channel, Object message) {
        try {
            redisTemplate.convertAndSend(channel, message);
            log.info("Published message to channel {}: {}", channel, message);
        } catch (Exception e) {
            log.error("Error publishing message to Redis", e);
        }
    }
}

