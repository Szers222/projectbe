package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
}
