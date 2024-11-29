package tdc.edu.vn.project_mobile_be.dtos.requests.coupon;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequestParamsDTO {
    private int page = 0;
    private int size = 20;
    private String sort = "couponExpire";
    private String direction = "asc";
    private String search;
    private int role;
}
