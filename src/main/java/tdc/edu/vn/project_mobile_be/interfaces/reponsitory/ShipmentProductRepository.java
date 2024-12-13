package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProductId;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ShipmentProductRepository extends JpaRepository<ShipmentProduct, ShipmentProductId> {

    @Query("SELECT sp FROM ShipmentProduct sp WHERE sp.shipment.shipmentId = ?1")
    List<ShipmentProduct> findByShipmentId(UUID shipmentId);

    @Query("SELECT sp FROM ShipmentProduct sp WHERE sp.id= ?1")
    List<ShipmentProduct> findByProductId(ShipmentProductId shipmentProductId);

    @Query("SELECT sp FROM ShipmentProduct sp WHERE sp.product.productId = ?1 AND sp.productSize.productSizeId = ?2")
    List<ShipmentProduct> findByProductIdAndProductSizeId(UUID productId, UUID productSizeId);

    @Query("SELECT sp FROM ShipmentProduct sp inner join sp.shipment s WHERE s.shipmentDate BETWEEN ?1AND ?2")
    List<ShipmentProduct> findByShipmentShipmentDateBetween(Timestamp startDate, Timestamp endDate);
}

