package tdc.edu.vn.project_mobile_be.dtos.responses.revenue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueResponseDTO {
    @JsonProperty("revenue_id")
    private UUID revenueId;
    @JsonProperty("revenue")
    private double revenue;
    @JsonProperty("date")
    private Timestamp date;
}
