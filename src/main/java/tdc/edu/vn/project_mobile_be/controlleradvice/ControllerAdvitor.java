package tdc.edu.vn.project_mobile_be.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.commond.customexception.*;
import tdc.edu.vn.project_mobile_be.dtos.ErrorResponseDTO;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerAdvitor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExistsSlugException.class)
    public ResponseEntity<Object> handleExistsSlugException(
            ExistsSlugException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Kiểm tra lại Slug vì Slug đã tồn tại rồi !");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNullEntityException(
            EntityNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Hiện tại không có thực thể nào tồn tại!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(
            UnsupportedOperationException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Không thực thi method này được!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<Object> handleFileEmptyException(
            FileEmptyException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Không có file nào được chọn!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberErrorException.class)
    public ResponseEntity<Object> handleFileEmptyException(
            NumberErrorException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Số không hợp lệ!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ListNotFoundException.class)
    public ResponseEntity<Object> handleListNotFoundException(
            ListNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Danh sách không tồn tại!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<Object> handleValidateException(
            ValidateException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Danh sách không tồn tại!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setMessage(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

}
