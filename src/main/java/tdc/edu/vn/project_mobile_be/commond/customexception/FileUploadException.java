package tdc.edu.vn.project_mobile_be.commond.customexception;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
