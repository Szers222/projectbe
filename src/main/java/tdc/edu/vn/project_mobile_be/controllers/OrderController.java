package tdc.edu.vn.project_mobile_be.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestByUserDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.order.OrderResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.service.OrderService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping({"/order/guest", "/order/guest/"})
    public ResponseEntity<ResponseData<?>> createOrder(
            @Valid @RequestBody OrderCreateRequestDTO orderCreateRequestDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Order orderCreated = orderService.createOrderByGuest(orderCreateRequestDTO);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Order created successfully!", orderCreated);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping({"/order/user", "/order/user/"})
    public ResponseEntity<ResponseData<?>> createOrderByUser(
            @Valid @RequestBody OrderCreateRequestByUserDTO orderCreateRequestDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Order orderCreated = orderService.createOrderByUser(orderCreateRequestDTO);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Order created successfully!", orderCreated);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping({"/order/cart/{cartId}", "/order/cart/{cartId}/"})
    public ResponseEntity<ResponseData<OrderResponseDTO>> getOrderByCart(@PathVariable UUID cartId) {
        OrderResponseDTO order = orderService.getOrderByCart(cartId);
        ResponseData<OrderResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Order found!", order);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping({"/order/change", "/order/change"})
    public ResponseEntity<ResponseData<?>> changeOrderStatus(
            @RequestBody OrderChangeStatusDTO orderChangeStatusDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Order order = orderService.orderChangeStatus(orderChangeStatusDTO);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Order status changed!", order);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping({"/order/user/{userId}", "/order/user/{userId}/"})
    public ResponseEntity<ResponseData<List<OrderResponseDTO>>> getOrderByUserId(@PathVariable UUID userId) {
        List<OrderResponseDTO> orders = orderService.getOrderByUserId(userId);
        ResponseData<List<OrderResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Orders found!", orders);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping({"/order/shipper/{shipperId}", "/order/user/{shipperId}/"})
    public ResponseEntity<ResponseData<List<OrderResponseDTO>>> getOrderByShipperId(@PathVariable UUID shipperId) {
        List<OrderResponseDTO> orders = orderService.getOrderByShipperId(shipperId);
        ResponseData<List<OrderResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Orders found!", orders);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping({"/orders/status", "/orders/status/"})
    public ResponseEntity<ResponseData<List<OrderResponseDTO>>> getOrderByStatus(@RequestParam int status) {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setStatus(status);
        List<OrderResponseDTO> orders = orderService.getOrderByStatus(orderStatusDTO.getStatus());
        ResponseData<List<OrderResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Orders found!", orders);
        return ResponseEntity.ok(responseData);
    }
}
