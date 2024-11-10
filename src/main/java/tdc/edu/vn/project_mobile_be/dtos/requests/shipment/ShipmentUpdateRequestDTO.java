package tdc.edu.vn.project_mobile_be.dtos.requests.shipment;

import com.google.j2objc.annotations.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentUpdateRequestDTO implements IDto<Shipment> {
    @Property("shipmentDate")
    private LocalDate shipmentDate;
    @Property("shipmentDiscount")
    private float shipmentDiscount;
    @Property("shipmentShipCost")
    private double shipmentShipCost;
    @Property("shipmentSupplier")
    private String shipmentSupplier;


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
