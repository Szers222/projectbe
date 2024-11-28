package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.order.Order;

import java.util.UUID;

public interface ShipperRepository extends JpaRepository<Order, UUID> {
}
