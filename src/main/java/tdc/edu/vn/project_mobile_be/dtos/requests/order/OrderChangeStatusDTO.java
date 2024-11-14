package tdc.edu.vn.project_mobile_be.dtos.requests.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderChangeStatusDTO {

    @JsonProperty("status")
    private int status;
    @JsonProperty("orderId")
    private UUID orderId;
}