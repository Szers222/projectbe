package tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductCreateRequestDTO implements IDto<ShipmentProduct> {

    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("price")
    private double price;

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
