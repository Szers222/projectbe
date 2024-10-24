package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.status.CartStatus;

import java.util.UUID;

public interface CartStatusRepository extends JpaRepository<CartStatus, UUID> {
}

