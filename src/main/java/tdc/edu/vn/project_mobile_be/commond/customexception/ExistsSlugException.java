package tdc.edu.vn.project_mobile_be.commond.customexception;

public class ExistsSlugException extends RuntimeException{
    public ExistsSlugException(String s) {
        super(s);
    }
}
