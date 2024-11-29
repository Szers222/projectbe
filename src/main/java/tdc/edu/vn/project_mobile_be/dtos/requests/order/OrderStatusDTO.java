package tdc.edu.vn.project_mobile_be.dtos.requests.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {
    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private int status;
}
