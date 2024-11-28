package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.coupon.CouponResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface CouponService extends IService<Coupon, UUID> {
    Coupon createCoupon(CouponCreateRequestDTO params);

    Coupon createCouponForProduct(CouponUpdateRequestDTO params);

    Coupon updateCoupon(CouponUpdateRequestDTO params, UUID id);

    boolean deleteCoupon(UUID couponId);

    Page<CouponResponseDTO> getCoupons(int role, Pageable pageable);

    Coupon updateCouponByProductId(CouponUpdateRequestDTO couponDTO, UUID productId);

    List<CouponResponseDTO> getCouponByType (int ...type);

    CouponResponseDTO getCouponById(UUID couponId);
}
