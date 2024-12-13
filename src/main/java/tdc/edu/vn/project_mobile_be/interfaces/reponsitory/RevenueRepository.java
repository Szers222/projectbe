package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.entities.revenue.Revenue;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface RevenueRepository extends JpaRepository<Revenue, UUID> {

    @Query("SELECT r FROM Revenue r WHERE r.revenueDate BETWEEN ?1 AND ?2")
    List<Revenue> findRevenueByDate(Timestamp from, Timestamp to);
}
