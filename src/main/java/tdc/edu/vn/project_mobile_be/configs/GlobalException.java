package tdc.edu.vn.project_mobile_be.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {
    //RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(value = AppException.class)
//    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
//        ErrorCode errorCode = exception.getErrorCode();
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setCode(1001);
//        apiResponse.setMessage(errorCode.getMessage());
//        //Lỗi 400
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
    //valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ResponseData<Map<String, String>> responseData = new ResponseData<>(
                HttpStatus.BAD_REQUEST, "Validation failed", errors);

        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
