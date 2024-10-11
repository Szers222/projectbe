package tdc.edu.vn.project_mobile_be.commond.customexception;

public class ListNotFoundException extends RuntimeException {
    public ListNotFoundException(String message) {
        super(message);
    }
}
