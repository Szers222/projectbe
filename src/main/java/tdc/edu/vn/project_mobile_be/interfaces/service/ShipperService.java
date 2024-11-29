package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.shipper.ShipperRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface ShipperService extends IService<Order, UUID> {
    Order receive (Order order,ShipperRequestDTO request);
    void pickup (Order order,ShipperRequestDTO request);
    void deliver (Order order,ShipperRequestDTO request);
    void delivered_success (Order order,ShipperRequestDTO request);
    void cancel_order (Order order,ShipperRequestDTO request);
    void return_order (Order order,ShipperRequestDTO request);
}
