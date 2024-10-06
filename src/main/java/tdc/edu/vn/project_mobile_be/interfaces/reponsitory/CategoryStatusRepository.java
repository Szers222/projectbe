package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;

import java.util.UUID;

public interface CategoryStatusRepository extends JpaRepository<CategoryStatus, UUID> {

}
