package tdc.edu.vn.project_mobile_be.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserRole {
    ADMIN(0),
    USER(1),
    SHIPPER(2);
    private int value;

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid UserRole value: " + value);
    }

}
