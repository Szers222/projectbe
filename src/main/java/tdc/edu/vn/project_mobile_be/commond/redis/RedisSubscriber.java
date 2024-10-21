package tdc.edu.vn.project_mobile_be.commond.redis;


import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber {
    public void receiveMessage(String message) {
        System.out.println("Nhận được thông báo: " + message);
        // Xử lý dữ liệu hoặc cập nhật bộ nhớ đệm
    }

}