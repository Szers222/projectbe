package tdc.edu.vn.project_mobile_be.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Shipper {
    RECEIVE(1),
    PICKUP(2),
    DELIVERING(3),
    DELIVERED_SUCCESS(4),
    CANCEL_ORDER(5);
    private int value;
}
