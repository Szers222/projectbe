package tdc.edu.vn.project_mobile_be.enums;

public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(9999, "error"),
    USER_EXISTED(1002, "Email đã tồn tại"),
    USERNAME_EXISTED(1003, "Email không hợp lệ"),
    PASSWORD_EXISTED(1004, "Pass phải có ít nhất 8 ký tự và tối đa 20"),
    INVALID_KEY(1001, "invalid message key"),
    USER_NOT_EXISTED(1002, "Email không tồn tại"),
    UNAUTHENTICATED(1005, "unauthenticated user");

    private final int code;
    private final String message;

    // Constructor yêu cầu có tham số
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter cho mã lỗi
    public int getCode() {
        return code;
    }

    // Getter cho thông điệp lỗi
    public String getMessage() {
        return message;
    }
}