package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.order.OrderResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface OrderService extends IService<Order, UUID> {

    Order createOrder(OrderCreateRequestDTO order);

    Order orderChangeStatus(OrderChangeStatusDTO orderChangeStatusDTO);


    List<OrderResponseDTO> getOrderByUserId(UUID userId);

    List<CartResponseDTO> getCartByOrderId(UUID orderId);

}
