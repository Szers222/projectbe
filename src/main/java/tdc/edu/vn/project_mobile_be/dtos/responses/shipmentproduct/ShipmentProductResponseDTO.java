package tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductResponseDTO implements IDto<ShipmentProductResponseDTO> {


    @JsonProperty("productId")
    private UUID productId;
    @JsonProperty("shipmentProductPrice")
    private double shipmentProductPrice;
    @JsonProperty("sizesProduct")
    private List<SizeProductShipmentResponseDTO> sizesProduct;

    @Override
    public ShipmentProductResponseDTO toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(ShipmentProductResponseDTO entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
