package tdc.edu.vn.project_mobile_be.dtos.requests.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderChangeStatusDTO {
    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private int status;
    @NotNull(message = "Order id is required")
    @JsonProperty("orderId")
    private UUID orderId;
    @JsonProperty("reason")
    private String reason;
}
