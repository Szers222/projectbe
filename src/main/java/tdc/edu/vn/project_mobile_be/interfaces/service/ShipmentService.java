package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipment.ShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ShipmentService extends IService<Shipment, UUID> {


    Shipment createShipment(ShipmentCreateRequestDTO shipment);

    Shipment updateShipment(ShipmentUpdateRequestDTO shipment, UUID shipmentId);

    boolean deleteShipment(UUID shipmentId);

    List<ShipmentResponseDTO> getShipmentById(UUID shipmentId);

    ShipmentResponseDTO getShipmentBySupplier(String supplier);

    List<ShipmentResponseDTO> getAllShipment();
}
