package tdc.edu.vn.project_mobile_be.dtos.responses.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponseDTO implements IDto<Coupon> {
    @JsonProperty("couponId")
    private UUID couponId;
    @JsonProperty("couponName")
    private String couponName;
    @JsonProperty("couponCode")
    private String couponCode;
    @JsonProperty("couponPrice")
    private double couponPrice;
    @JsonProperty("couponRelease")
    private LocalDate couponRelease;
    @JsonProperty("couponExpire")
    private LocalDate couponExpire;
    @JsonProperty("couponType")
    private int couponType;
    @JsonProperty("couponFeeShip")
    private double couponFeeShip;
    @JsonProperty("couponPerHundred")
    private float couponPerHundred;
    @JsonProperty("couponQuantity")
    private int couponQuantity;

    @Override
    public Coupon toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Coupon entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
