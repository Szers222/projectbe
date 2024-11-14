package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProductId;

public interface ShipmentProductRepository extends JpaRepository<ShipmentProduct, ShipmentProductId> {

}

