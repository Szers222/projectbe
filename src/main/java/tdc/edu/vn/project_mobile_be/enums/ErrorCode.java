package tdc.edu.vn.project_mobile_be.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(9999,"error"),
    USER_EXISTED(1002,"Email da ton tai"),
    USERNAME_EXISTED(1003,"Email không hợp lệ"),
    PASSWORD_EXISTED(1004, "Pass phải có ít nhất 8 ký tự và tối đa 20"),
    INVALID_KEY(1001,"invalid message key"),
    USER_NOT_EXISTED(1002,"Email khong ton tai"),
    UNAUTHENTICATED(1005,"unauthenticated user"),

    ;

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
