package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ShipmentRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ShipmentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShipmentServiceImpl extends AbService<Shipment, UUID> implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment createShipment(ShipmentCreateRequestDTO requestDTO) {

        LocalDateTime shipmentDateTime;

        if (requestDTO.getShipmentDate() == null) {
            shipmentDateTime = LocalDateTime.now();

        } else {
            shipmentDateTime = requestDTO.getShipmentDate().atStartOfDay();
        }

        Timestamp timestampShipment = Timestamp.valueOf(shipmentDateTime);

        if (!checkShipmentDiscount(requestDTO.getShipmentDiscount(), requestDTO.getShipmentShipCost())) {
            throw new IllegalArgumentException("Discount of Ship Cost is invalid");
        }

        Shipment shipment = requestDTO.toEntity();
        UUID shipmentId = UUID.randomUUID();
        shipment.setShipmentId(shipmentId);
        shipment.setShipmentDate(timestampShipment);

        return shipmentRepository.save(shipment);
    }

    @Override
    public Shipment updateShipment(ShipmentUpdateRequestDTO shipment, UUID shipmentId) {
        return null;
    }

    @Override
    public boolean deleteShipment(UUID shipmentId) {
        return false;
    }

    @Override
    public Shipment getShipmentById(UUID shipmentId) {
        return null;
    }

    @Override
    public Shipment getShipmentBySupplier(String supplier) {
        return null;
    }

    @Override
    public Page<Shipment> getAllShipment(Pageable pageable) {
        return null;
    }

    public boolean checkShipmentDiscount(float discount, double shipCost) {
        if (discount < 0 || discount > 100) {
            return false;
        }
        if (shipCost < 0) {
            return false;
        }
        return true;
    }
}
