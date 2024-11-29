package tdc.edu.vn.project_mobile_be.dtos.requests.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponUpdateRequestDTO implements IDto<Coupon> {

    @NotNull(message = "Tên danh mục không được để trống")
    @JsonProperty("couponName")
    private String couponName;
    @NotNull(message = "Mã giảm giá không được để trống")
    @JsonProperty("couponCode")
    private String couponCode;

    @JsonProperty("couponRelease")
    private LocalDate couponRelease;

    @JsonProperty("couponExpire")
    private LocalDate couponExpire;
    @JsonProperty("couponQuantity")
    @Min(value = 1, message = "Số lượng mã giảm giá phải lớn hơn 0")
    private int couponQuantity;

    @JsonProperty("couponFeeShip")
    private double couponFeeShip;

    @JsonProperty("couponPerHundred")
    private float couponPerHundred;
    @JsonProperty("couponPrice")
    private double couponPrice;
    @NotNull(message = "Loại mã giảm giá không được để trống")
    @JsonProperty("couponType")
    private int couponType;
    @Override
    public Coupon toEntity() {
        return null;
    }

    @Override
    public void toDto(Coupon entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
