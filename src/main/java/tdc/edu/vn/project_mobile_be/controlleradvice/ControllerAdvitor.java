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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNullEntityException(
            EntityNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Hiện tại không có thực thể nào tồn tại!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(
            UnsupportedOperationException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Không thực thi method này được!");
        errorResponseDTO.setMessage(details);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<Object> handleFileEmptyException(
            FileEmptyException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Không có file nào được chọn!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberErrorException.class)
    public ResponseEntity<Object> handleFileEmptyException(
            NumberErrorException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Số không hợp lệ!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ListNotFoundException.class)
    public ResponseEntity<Object> handleListNotFoundException(
            ListNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Danh sách không tồn tại!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ReleaseException.class)
    public ResponseEntity<Object> handleReleaseException(
            ReleaseException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Ngày phát hành không hợp lệ!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<Object> handleInvalidRoleException(
            InvalidRoleException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Tài khoản không có quyền truy cập!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidLinkException.class)
    public ResponseEntity<Object> handleInvalidLinkException(
            InvalidLinkException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Link không hợp lệ!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.valueOf("BAD_REQUEST"));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipleFieldsNullOrEmptyException.class)
    public ResponseEntity<Object> handNullOrEmptyException(
            MultipleFieldsNullOrEmptyException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError(ex.getErrorMessages());
        List<String> details = new ArrayList<>();
        details.add("Dữ liệu không được để trống!");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CouponPricePerHundredException.class)
    public ResponseEntity<Object> handleCouponPricePerHundredException(
            CouponPricePerHundredException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        errorResponseDTO.setError(erros);
        List<String> details = new ArrayList<>();
        details.add("Coupon không hợp lệ !");
        errorResponseDTO.setMessage(details);
        errorResponseDTO.setStatus(HttpStatus.valueOf("BAD_REQUEST"));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

}
