package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    @Query("SELECT s FROM Shipment s WHERE s.shipmentId = ?1")
    Shipment findShipmentById(UUID shipmentId);

    @Query("select s from Shipment s where s.createdAt between ?1 and ?2")
    List<Shipment> findShipmentByDate(Timestamp from, Timestamp to);
}
