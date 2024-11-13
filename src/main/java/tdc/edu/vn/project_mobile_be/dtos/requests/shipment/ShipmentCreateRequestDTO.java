package tdc.edu.vn.project_mobile_be.dtos.requests.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.j2objc.annotations.Property;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct.ShipmentProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentCreateRequestDTO implements IDto<Shipment> {
    @NotNull(message = "Ngày giao hàng không được để trống")
    @Property("shipmentDate")
    private LocalDate shipmentDate;

    @Property("shipmentDiscount")
    private float shipmentDiscount;

    @NotNull(message = "Phí vận chuyển không được để trống")
    @Property("shipmentShipCost")
    private float shipmentShipCost;

    @NotNull(message = "Nhà cung cấp không được để trống")
    @JsonProperty("supplierId")
    private UUID supplierId;

    @Min(value = 0, message = "ProductPrice phải lớn hơn hoặc bằng 0")
    @JsonProperty("productPrice")
    private double productPrice;

    @NotNull(message = "ShipmentProducts không được để trống")
    @JsonProperty("shipmentProducts")
    private List<ShipmentProductRequestParamsDTO> shipmentProductCreateRequestDTO;




    @Override
    public Shipment toEntity() {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(this, shipment, "createdAt", "updatedAt");
        return shipment;
    }

    @Override
    public void toDto(Shipment entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
