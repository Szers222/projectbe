package tdc.edu.vn.project_mobile_be.configs.websockethandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateListener  {
    @Autowired
    private ProductWebSocketHandler webSocketHandler;


    public void onMessage(Message message, ChannelTopic topic) {
        String productId = message.toString();
        webSocketHandler.sendMessage("/topic/productUpdates", "Sản phẩm " + productId + " đã được cập nhật!");
    }
}
