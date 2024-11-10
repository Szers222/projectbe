package tdc.edu.vn.project_mobile_be.dtos.requests.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.j2objc.annotations.Property;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct.ShipmentProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentCreateRequestDTO implements IDto<Shipment> {
    @NotBlank(message = "Ngay giao hang khong duoc de trong")
    @Property("shipmentDate")
    private LocalDate shipmentDate;

    @Property("shipmentDiscount")
    private float shipmentDiscount;

    @NotBlank(message = "Phi van chuyen khong duoc de trong")
    @Property("shipmentShipCost")
    private double shipmentShipCost;

    @NotBlank(message = "Nha cung cap khong duoc de trong")
    @Property("shipmentSupplier")
    private String shipmentSupplier;

    @JsonProperty("shipmentProducts")
    private ShipmentProductCreateRequestDTO shipmentProductCreateRequestDTO;


    @Override
    public Shipment toEntity() {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(this, shipment, "createdAt", "updatedAt");
        return shipment;
    }

    @Override
    public void toDto(Shipment entity) {

    }
}
