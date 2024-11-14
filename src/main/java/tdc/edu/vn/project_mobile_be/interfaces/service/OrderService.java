package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface OrderService extends IService<Order, UUID> {

    Order createOrder(OrderCreateRequestDTO order);

    Order orderChangeStatus(OrderChangeStatusDTO orderChangeStatusDTO);


    List<Order> getOrderByUserId(UUID userId);
}
