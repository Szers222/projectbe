package tdc.edu.vn.project_mobile_be.commond.customexception;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException(String s) {
        super(s);
    }
}
