package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.entities.product.ProductNotification;

import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 500) // Kiểm tra bảng tạm thời mỗi 5 giây
    public void sendNotifications() {

        jdbcTemplate.query("SELECT * FROM product_notifications", (rs, rowNum) -> new ProductNotification(rs.getInt("notification_id"), UUID.fromString(rs.getString("product_id")), rs.getString("product_name"), rs.getDouble("product_price"), rs.getDouble("product_sale"), rs.getInt("product_quantity"))).forEach(notification -> {
            // Gửi thông báo đến topic
            this.template.convertAndSend("/topic/product/update", "12313132131");
            // Xóa thông báo đã gửi khỏi bảng tạm thời
            jdbcTemplate.update("DELETE FROM product_notifications WHERE product_id = ?", notification.getProductId());
        });
    }
}
