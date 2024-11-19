package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProductId;

import java.util.List;
import java.util.UUID;

public interface ShipmentProductRepository extends JpaRepository<ShipmentProduct, ShipmentProductId> {

    @Query("SELECT sp FROM ShipmentProduct sp WHERE sp.shipment.shipmentId = ?1")
    List<ShipmentProduct> findByShipmentId(UUID shipmentId);
}

