package tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductResponseDTO {

    @JsonProperty("shipmentProductQuantity")
    private int shipmentProductQuantity;
    @JsonProperty("shipmentProductPrice")
    private double shipmentProductPrice;

}
