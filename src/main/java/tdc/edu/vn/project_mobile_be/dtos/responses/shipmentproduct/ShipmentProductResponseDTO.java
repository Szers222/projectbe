package tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductResponseDTO implements IDto<ShipmentProductResponseDTO> {
    @JsonProperty("shipmentProductQuantity")
    private int shipmentProductQuantity;
    @JsonProperty("shipmentProductPrice")
    private double shipmentProductPrice;

    @Override
    public ShipmentProductResponseDTO toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(ShipmentProductResponseDTO entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
