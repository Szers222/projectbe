package tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeProductShipmentResponseDTO implements IDto<SizeProduct> {
    @JsonProperty("productQuantity")
    private int productSizeQuantity;

    @NotNull(message = "Size không được để trống")
    @JsonProperty("sizeId")
    private UUID sizeId;

    @Override
    public SizeProduct toEntity() {
        return null;
    }

    @Override
    public void toDto(SizeProduct entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
