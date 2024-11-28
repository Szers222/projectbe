package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    @Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
    Coupon findCouponByCouponId(@Param("couponId") UUID couponId);

    @Query("SELECT c FROM Coupon c where c.couponExpire > current_date")
    List<Coupon> findCouponByCouponExpire();

    @Query("select c from Coupon c left join fetch c.product cp where cp.productId = :productId")
    Optional<Coupon> findCouponByProductId(@Param("productId") UUID productId);

    @Query("SELECT c FROM Coupon c WHERE c.couponType = :type")
    List<Coupon> findCouponByType(@Param("type") int type);

    @Query("SELECT c FROM Coupon c WHERE c.couponCode = :code")
    Optional<Coupon> findCouponByCode(@Param("code") String code);
}
