package tdc.edu.vn.project_mobile_be.configs.websockethandler;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ServerEndpoint(value = "/websocket")
@Component
public class ProductWebSocketHandler extends TextWebSocketHandler {

    private static final List<Session> sessions = Collections.synchronizedList(new ArrayList<>());

    private long lastUpdatedTimestamp = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Thêm session vào danh sách kết nối
    }

    public void sendMessage(String channel, String message) {
        template.convertAndSend(channel, message);
    }


    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    public Long coverLong(Timestamp time) {
        String timeString = time.toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(timeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long timeInMilliSeconds = date.getTime();
        return timeInMilliSeconds;
    }
}
