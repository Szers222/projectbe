//package tdc.edu.vn.project_mobile_be.controllers;
//
//
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    private final SimpMessagingTemplate template;
//
//    public WebSocketController(SimpMessagingTemplate template) {
//        this.template = template;
//    }
//
//    public void sendMessage(String message) {
//        this.template.convertAndSend("/topic/product_changes", message);
//    }
//}
