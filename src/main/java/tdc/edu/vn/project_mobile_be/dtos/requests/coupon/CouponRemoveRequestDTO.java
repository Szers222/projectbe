package tdc.edu.vn.project_mobile_be.dtos.requests.coupon;

import tdc.edu.vn.project_mobile_be.dtos.requests.RemoveByIdRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

public class CouponRemoveRequestDTO extends RemoveByIdRequestDTO implements IDto<Coupon> {
    @Override
    public Coupon toEntity() {
        return null;
    }

    @Override
    public void toDto(Coupon entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
