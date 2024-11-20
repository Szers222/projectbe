package tdc.edu.vn.project_mobile_be.commond.component;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Receiver {

    @Autowired
    private SimpMessagingTemplate template;

    public void receiveMessage(String message) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(message, Map.class);
        this.template.convertAndSend("/topic/product/updates", map);
    }
}