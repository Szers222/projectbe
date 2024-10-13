package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryStatusRepository extends JpaRepository<CategoryStatus, UUID> {
    Optional<CategoryStatus> findByCategoryStatusType(int type);
    boolean existsById(UUID id);
}
