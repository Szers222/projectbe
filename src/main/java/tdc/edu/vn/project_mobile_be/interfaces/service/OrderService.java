package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestByUserDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.order.OrderResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface OrderService extends IService<Order, UUID> {

    Order createOrderByGuest(OrderCreateRequestDTO order);

    Order orderChangeStatus(OrderChangeStatusDTO orderChangeStatusDTO);


    List<OrderResponseDTO> getOrderByUserId(UUID userId);

    OrderResponseDTO getOrderByCart(UUID cartId);

    Order createOrderByUser(OrderCreateRequestByUserDTO orderCreateRequestDTO);

    List<OrderResponseDTO> getOrderByShipperId(UUID shipperId);

    List<OrderResponseDTO> getOrderByStatus(int status);
}
