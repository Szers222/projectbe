package tdc.edu.vn.project_mobile_be.commond.customexception;

public class InvalidLinkException extends RuntimeException {
    public InvalidLinkException(String message) {
        super(message);
    }
}
