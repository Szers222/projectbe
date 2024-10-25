package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUserEmail(String userEmail);
    Optional<User> findByUserEmail(String userEmail);
}


