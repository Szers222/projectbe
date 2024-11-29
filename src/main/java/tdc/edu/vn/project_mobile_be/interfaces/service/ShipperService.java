package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.shipper.ShipperRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface ShipperService extends IService<Order, UUID> {
    void receive (ShipperRequestDTO request);
    void pickup (ShipperRequestDTO request);
    void deliver (ShipperRequestDTO request);
    void delivered_success (ShipperRequestDTO request);
    void cancel_order (ShipperRequestDTO request);
    void return_order (ShipperRequestDTO request);
}
