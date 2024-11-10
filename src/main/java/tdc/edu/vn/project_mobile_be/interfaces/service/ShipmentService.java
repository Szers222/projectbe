package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface ShipmentService extends IService<Shipment, UUID> {


    Shipment createShipment(ShipmentCreateRequestDTO shipment);

    Shipment updateShipment(ShipmentUpdateRequestDTO shipment, UUID shipmentId);

    boolean deleteShipment(UUID shipmentId);

    Shipment getShipmentById(UUID shipmentId);

    Shipment getShipmentBySupplier(String supplier);

    Page<Shipment> getAllShipment(Pageable pageable);
}
