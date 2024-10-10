package tdc.edu.vn.project_mobile_be.commond;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    @JsonIgnore
    private HttpStatus status;
    @JsonProperty("status")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
    public ResponseData(HttpStatus status, String message, T data) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.statusCode = status.value();
    }
}
