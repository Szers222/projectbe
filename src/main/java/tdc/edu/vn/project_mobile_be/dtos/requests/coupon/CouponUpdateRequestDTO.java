package tdc.edu.vn.project_mobile_be.dtos.requests.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Tên danh mục không được để trống")
    @JsonProperty("couponName")
    private String couponName;
    @NotBlank(message = "Mã giảm giá không được để trống")
    @JsonProperty("couponCode")
    private String couponCode;
    @NotBlank(message = "Ngày phát hành không được để trống")
    @JsonProperty("couponRelease")
    private LocalDate couponRelease;
    @NotBlank(message = "Ngày hết hạn không được để trống")
    @JsonProperty("couponExpire")
    private LocalDate couponExpire;
    @JsonProperty("couponQuantity")
    @Min(value = 1, message = "Số lượng mã giảm giá phải lớn hơn 0")
    private int couponQuantity;
    @JsonProperty("couponPerHundred")
    private float couponPerHundred;
    @JsonProperty("couponPrice")
    private String couponPrice;
    @NotBlank(message = "Loại mã giảm giá không được để trống")
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
