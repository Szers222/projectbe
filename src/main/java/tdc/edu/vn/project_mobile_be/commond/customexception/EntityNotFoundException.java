package tdc.edu.vn.project_mobile_be.commond.customexception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String s) {
        super(s);
    }
}
