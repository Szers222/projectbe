package tdc.edu.vn.project_mobile_be.commond;

import tdc.edu.vn.project_mobile_be.enums.ErrorCode;

public class AppException extends RuntimeException {
    private ErrorCode errorCode;
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
