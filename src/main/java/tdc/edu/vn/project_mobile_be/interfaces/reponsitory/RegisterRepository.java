package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.util.UUID;

public interface RegisterRepository extends JpaRepository<User, UUID> {
    User findByUserEmail(String userEmail);

}
