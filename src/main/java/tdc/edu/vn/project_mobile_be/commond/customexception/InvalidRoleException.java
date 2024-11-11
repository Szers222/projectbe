package tdc.edu.vn.project_mobile_be.commond.customexception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(message);
    }
}
