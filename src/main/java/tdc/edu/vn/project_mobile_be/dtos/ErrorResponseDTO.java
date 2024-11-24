package tdc.edu.vn.project_mobile_be.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {


    @JsonProperty("error")
    private List<String> error;
    @JsonProperty("message")
    private List<String> message = new ArrayList<String>();
    @JsonIgnore
    private HttpStatus status;
    @JsonProperty("status")
    private int statusCode;

    public ErrorResponseDTO(List<String> error, HttpStatus status) {
        this.error = error;
        this.status = status;
        this.statusCode = status.value();
    }

}
