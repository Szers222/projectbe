package tdc.edu.vn.project_mobile_be.commond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HttpException extends Exception {
    private HttpStatus status;
    private String message;
}
