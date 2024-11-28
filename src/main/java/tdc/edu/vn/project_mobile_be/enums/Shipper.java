package tdc.edu.vn.project_mobile_be.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Shipper {
    RECEIVE("Nhận hàng"),
    PICKUP("Lấy hàng"),
    DELIVERING("Giao hàng"),
    DELIVERED_SUCCESS("Giao hàng thành công"),
    CANCEL_ORDER("Huỷ hàng"),
    RETURN_ODER("Trả hàng")
    ;
    private String mess;
}
