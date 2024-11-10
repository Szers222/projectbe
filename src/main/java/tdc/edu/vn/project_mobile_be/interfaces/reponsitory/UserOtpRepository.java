package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.entities.user.UserOtp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserOtpRepository extends JpaRepository<UserOtp, UUID> {
    UserOtp findByUser(User user);
    List<UserOtp> findAll();
    @Transactional
    @Modifying
    @Query("DELETE FROM UserOtp uo WHERE uo.otpTime < :expirationTime")
    void deleteExpiredOtps(@Param("expirationTime") LocalDateTime expirationTime);
}
