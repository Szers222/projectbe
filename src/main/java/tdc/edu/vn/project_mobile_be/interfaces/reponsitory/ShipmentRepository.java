package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;

import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

}
