package tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductCreateRequestDTO implements IDto<ShipmentProduct> {

    @Min(value = 0, message = "ProductPrice phải lớn hơn hoặc bằng 0")
    @JsonProperty("productPrice")
    private double productPrice;

    @NotNull(message = "ProductId không được để trống")
    @JsonProperty("productId")
    private UUID productId;

    @NotNull(message = "ShipmentId không được để trống")
    @JsonProperty("productQuantity")
    private int productQuantity;

    @JsonProperty("sizeProduct")
    private UUID sizeProductId;


    @Override
    public ShipmentProduct toEntity() {
        ShipmentProduct shipmentProduct = new ShipmentProduct();
        BeanUtils.copyProperties(this, shipmentProduct, "shipmentProductId");
        return shipmentProduct;
    }

    @Override
    public void toDto(ShipmentProduct entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
