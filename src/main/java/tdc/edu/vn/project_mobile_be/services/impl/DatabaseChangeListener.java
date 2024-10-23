package tdc.edu.vn.project_mobile_be.services.impl;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tdc.edu.vn.project_mobile_be.dtos.requests.Notification;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

@Component
@Slf4j
public class DatabaseChangeListener {

    private final RedisMessagePublisher redisPublisher;

    public DatabaseChangeListener(RedisMessagePublisher redisPublisher) {
        this.redisPublisher = redisPublisher;
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        if (entity instanceof Product) {
            publishProductChange("CREATE", (Product) entity);
        }
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        if (entity instanceof Product) {
            publishProductChange("UPDATE", (Product) entity);
        }
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        if (entity instanceof Product) {
            publishProductChange("DELETE", (Product) entity);
        }
    }

    private void publishProductChange(String action, Product product) {
        try {
            ProductNotification notification = ProductNotification.fromProduct(action, product);
            redisPublisher.publish("product-changes", notification);
            log.info("Published {} notification for product: {}", action, product.getProductId());
        } catch (Exception e) {
            log.error("Error publishing product change notification", e);
        }
    }
}


