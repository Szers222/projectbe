package tdc.edu.vn.project_mobile_be.configs.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.dtos.ResponseObject;
import tdc.edu.vn.project_mobile_be.services.impl.PaymentService;
import tdc.edu.vn.project_mobile_be.services.impl.ZaloPayService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }

//    @PostMapping("/create-order")
//    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> orderDetails) {
//        try {
//            JSONObject orderResponse = zaloPayService.createOrder(orderDetails);
//            return new ResponseEntity<>(orderResponse.toString(), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Order Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}