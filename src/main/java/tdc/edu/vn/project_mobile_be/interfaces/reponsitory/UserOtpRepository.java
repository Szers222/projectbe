package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.entities.user.UserOtp;

import java.util.List;
import java.util.UUID;

public interface UserOtpRepository extends JpaRepository<UserOtp, UUID> {
    UserOtp findByUser(User user);
    List<UserOtp> findAll();
}
