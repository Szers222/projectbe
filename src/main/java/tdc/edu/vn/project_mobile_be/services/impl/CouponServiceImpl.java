package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.CouponPricePerHundredException;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.coupon.CouponResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CouponRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CouponService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class CouponServiceImpl extends AbService<Coupon, UUID> implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    private final int COUPON_PER_HUNDRED_TYPE = 0;
    private final int COUPON_PRICE_TYPE = 1;
    private final int COUPON_SHIP_TYPE = 2;
    private final int ROLE_ADMIN = 0;
    private final int ROLE_USER = 1;

    @Override
    public Coupon createCoupon(CouponCreateRequestDTO params) {
        boolean check = false;
        Timestamp expireDateTime;

        if (params.getCouponExpire() == null) {
            expireDateTime = null;
        } else {
            expireDateTime = coverToTimestampExpire(params.getCouponExpire());
        }

        Timestamp releaseDateTime = coverToTimestampRelease(params.getCouponRelease());
        Coupon coupon = new Coupon();
        if (validateCoupon(params) != check) {
            coupon = params.toEntity();
            coupon.setCouponId(UUID.randomUUID());
            coupon.setCouponRelease(releaseDateTime);
            coupon.setCouponExpire(expireDateTime);
            if (params.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                coupon.setCouponPrice(0);
                coupon.setCouponPerHundred(params.getCouponPerHundred());
                coupon.setCouponFeeShip(0);
            }
            if (params.getCouponType() == COUPON_PRICE_TYPE) {
                coupon.setCouponPerHundred(0);
                coupon.setCouponPrice(params.getCouponPrice());
                coupon.setCouponFeeShip(0);
            }
            if (params.getCouponType() == COUPON_SHIP_TYPE) {
                coupon.setCouponPerHundred(0);
                coupon.setCouponPrice(0);
                coupon.setCouponFeeShip(params.getCouponFeeShip());
            }
        }
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon createCouponForProduct(CouponUpdateRequestDTO params) {
        boolean check = false;
        Timestamp expireDateTime;

        if (params.getCouponExpire() == null) {
            expireDateTime = null;
        } else {
            expireDateTime = coverToTimestampExpire(params.getCouponExpire());
        }

        Timestamp releaseDateTime = coverToTimestampRelease(params.getCouponRelease());
        Coupon coupon = new Coupon();
        if (validateCoupon(params) != check) {
            coupon = params.toEntity();
            coupon.setCouponId(UUID.randomUUID());
            coupon.setCouponRelease(releaseDateTime);
            coupon.setCouponExpire(expireDateTime);
            if (params.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                coupon.setCouponPrice(0);
                coupon.setCouponPerHundred(params.getCouponPerHundred());
                coupon.setCouponFeeShip(0);
            }
            if (params.getCouponType() == COUPON_PRICE_TYPE) {
                coupon.setCouponPerHundred(0);
                coupon.setCouponPrice(params.getCouponPrice());
                coupon.setCouponFeeShip(0);
            }
            if (params.getCouponType() == COUPON_SHIP_TYPE) {
                coupon.setCouponPerHundred(0);
                coupon.setCouponPrice(0);
                coupon.setCouponFeeShip(params.getCouponFeeShip());
            }
        }
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(CouponUpdateRequestDTO params, UUID couponId) {
        boolean check = false;
        Timestamp expireDateTime = coverToTimestampExpire(params.getCouponExpire());
        Timestamp releaseDateTime = coverToTimestampRelease(params.getCouponRelease());

        Coupon coupon = couponRepository.findCouponByCouponId(couponId);
        if (coupon == null) {
            throw new EntityNotFoundException("Coupon không tồn tại");
        }
        log.info("coupon1231231312: {}", coupon);
        if (validateCoupon(params) != check) {
            coupon.setCouponName(params.getCouponName());
            coupon.setCouponCode(params.getCouponCode());
            coupon.setCouponQuantity(params.getCouponQuantity());
            coupon.setCouponRelease(releaseDateTime);
            coupon.setCouponExpire(expireDateTime);
            coupon.setCouponType(params.getCouponType());
            if (params.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                coupon.setCouponPrice(0);
                coupon.setCouponPerHundred(params.getCouponPerHundred());
                coupon.setCouponFeeShip(0);
            }
            if (params.getCouponType() == COUPON_PRICE_TYPE) {
                coupon.setCouponPerHundred(0);
                coupon.setCouponPrice(params.getCouponPrice());
                coupon.setCouponFeeShip(0);
            }
            if (params.getCouponType() == COUPON_SHIP_TYPE) {
                coupon.setCouponPerHundred(0);
                coupon.setCouponPrice(0);
                coupon.setCouponFeeShip(params.getCouponFeeShip());
            }
        }
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCouponByProductId(CouponUpdateRequestDTO couponDTO, UUID productId) {
        Optional<Coupon> couponOptional = couponRepository.findCouponByProductId(productId);
        if (couponOptional.isEmpty()) {
            throw new EntityNotFoundException("Coupon không tồn tại");
        }
        Coupon coupon = couponOptional.get();
        if (couponDTO != null) {
            boolean check = false;
            Timestamp expireDateTime = coverToTimestampExpire(couponDTO.getCouponExpire());
            Timestamp releaseDateTime = coverToTimestampRelease(couponDTO.getCouponRelease());
            if (validateCoupon(couponDTO) != check) {
                coupon.setCouponName(couponDTO.getCouponName());
                coupon.setCouponCode(couponDTO.getCouponCode());
                coupon.setCouponQuantity(couponDTO.getCouponQuantity());
                coupon.setCouponRelease(releaseDateTime);
                coupon.setCouponExpire(expireDateTime);
                coupon.setCouponType(couponDTO.getCouponType());
                if (couponDTO.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                    coupon.setCouponPrice(0);
                    coupon.setCouponPerHundred(couponDTO.getCouponPerHundred());
                    coupon.setCouponFeeShip(0);
                }
                if (couponDTO.getCouponType() == COUPON_PRICE_TYPE) {
                    coupon.setCouponPerHundred(0);
                    coupon.setCouponPrice(couponDTO.getCouponPrice());
                    coupon.setCouponFeeShip(0);
                }
                if (couponDTO.getCouponType() == COUPON_SHIP_TYPE) {
                    coupon.setCouponPerHundred(0);
                    coupon.setCouponPrice(0);
                    coupon.setCouponFeeShip(couponDTO.getCouponFeeShip());
                }
            }
        } else {
            return null;
        }
        return couponRepository.save(coupon);
    }

    @Override
    public List<CouponResponseDTO> getCouponByType(int... type) {
        List<CouponResponseDTO> responseDTOS = new ArrayList<>();
        for (int i : type) {
            couponRepository.findCouponByType(i).forEach(coupon -> {
                if (coupon.getCouponExpire().after(Timestamp.valueOf(LocalDate.now().atStartOfDay()))) {
                    CouponResponseDTO dto = new CouponResponseDTO();
                    dto.toDto(coupon);
                    responseDTOS.add(dto);
                }
            });
        }
        return responseDTOS;
    }

    @Override
    public boolean deleteCoupon(UUID couponId) {
        Coupon coupon = couponRepository.findCouponByCouponId(couponId);
        if (coupon == null) {
            throw new EntityNotFoundException("Coupon không tồn tại");
        }
        couponRepository.delete(coupon);
        return true;
    }

    @Override
    public CouponResponseDTO getCouponById(UUID couponId) {
        Coupon coupon = couponRepository.findCouponByCouponId(couponId);
        if (coupon == null) {
            throw new EntityNotFoundException("Coupon không tồn tại");
        }
        CouponResponseDTO dto = new CouponResponseDTO();
        dto.toDto(coupon);
        dto.setCouponExpire(coupon.getCouponExpire());
        return dto;
    }

    @Override
    public Page<CouponResponseDTO> getCoupons(int role, Pageable pageable) {
        List<CouponResponseDTO> responseDTOS = new ArrayList<>();
        if (role == this.ROLE_ADMIN) {
            couponRepository.findAll(pageable).forEach(coupon -> {
                CouponResponseDTO dto = new CouponResponseDTO();
                dto.toDto(coupon);
                responseDTOS.add(dto);
            });
        }
        if (role == this.ROLE_USER) {
            couponRepository.findCouponByCouponExpire().forEach(coupon -> {
                CouponResponseDTO dto = new CouponResponseDTO();
                dto.toDto(coupon);
                responseDTOS.add(dto);
            });
        }
        Page<CouponResponseDTO> result = new PageImpl<>(responseDTOS, pageable, responseDTOS.size());

        return result;
    }

    /**
     * Validates the given coupon parameters based on various business rules.
     *
     * @param params a CouponCreateRequestDTO object containing the coupon details to be validated
     * @return true if the coupon parameters are valid according to the business rules
     * @throws NumberErrorException           if any numeric or date business rules are violated
     * @throws CouponPricePerHundredException if the coupon price and percentage rules are violated
     */
    public boolean validateCoupon(CouponCreateRequestDTO params) {
        if (params.getCouponPerHundred() < 0 || params.getCouponPerHundred() > 100) {
            throw new NumberErrorException("CouponPerHundred phải nằm trong khoảng từ 0 đến 100");
        }
        double couponPrice = params.getCouponPrice();
        if (couponPrice < 0) {
            throw new NumberErrorException("CouponPrice phải lớn hơn 0 và không thể bằng 0");
        }
        if (params.getCouponPerHundred() != 0 && couponPrice != 0) {
            throw new CouponPricePerHundredException("CouponPerHundred và CouponPrice không thể cùng tồn tại");
        }
        if (params.getCouponPerHundred() == 0 && params.getCouponPrice() == 0 && params.getCouponFeeShip() == 0) {
            throw new CouponPricePerHundredException("CouponPerHundred hoặc CouponPrice hoặc CouponFeeShip phải tồn tại");
        }
        if (params.getCouponExpire().isBefore(params.getCouponRelease()) || params.getCouponExpire().isEqual(params.getCouponRelease())) {
            throw new NumberErrorException("CouponExpire phải sau CouponRelease và không thể trùng nhau");
        }
        if (params.getCouponExpire().isEqual(LocalDate.now())) {
            throw new NumberErrorException("CouponExpire không thể trùng với ngày hiện tại");
        }
        if (params.getCouponExpire().isBefore(LocalDate.now())) {
            throw new NumberErrorException("CouponExpire không thể trước ngày hiện tại");
        }
        return true;
    }

    public boolean validateCoupon(CouponUpdateRequestDTO params) throws NumberErrorException {
        if (params.getCouponPerHundred() < 0 || params.getCouponPerHundred() > 100) {
            throw new NumberErrorException("CouponPerHundred phải nằm trong khoảng từ 0 đến 100");
        }
        double couponPrice = params.getCouponPrice();
        if (couponPrice < 0) {
            throw new NumberErrorException("CouponPrice phải lớn hơn 0 và không thể bằng 0");
        }
        if (params.getCouponPerHundred() != 0 && couponPrice != 0) {
            throw new CouponPricePerHundredException("CouponPerHundred và CouponPrice không thể cùng tồn tại");
        }
        if (params.getCouponPerHundred() == 0 && params.getCouponPrice() == 0 && params.getCouponFeeShip() == 0) {
            throw new CouponPricePerHundredException("CouponPerHundred hoặc CouponPrice hoặc CouponFeeShip phải tồn tại");
        }
        if (params.getCouponExpire().isBefore(params.getCouponRelease()) || params.getCouponExpire().isEqual(params.getCouponRelease())) {
            throw new NumberErrorException("CouponExpire phải sau CouponRelease và không thể trùng nhau");
        }
        if (params.getCouponExpire().isBefore(LocalDate.now())) {
            throw new NumberErrorException("CouponExpire không thể trước ngày hiện tại");
        }
        return true;
    }

    // Updated Methods
    public Timestamp coverToTimestampRelease(LocalDate date) {
        return Timestamp.valueOf(Objects.requireNonNullElseGet(date, LocalDate::now).atStartOfDay());
    }

    public Timestamp coverToTimestampExpire(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return Timestamp.valueOf(date.atStartOfDay());
        }
    }
}