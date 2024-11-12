package tdc.edu.vn.project_mobile_be.dtos.responses.shipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseDTO implements IDto<Shipment> {
    @JsonProperty("shipmentId")
    private UUID shipmentId;

    @JsonProperty("shipmentDate")
    private LocalDate shipmentDate;

    @JsonProperty("shipmentDiscount")
    private float shipmentDiscount;

    @JsonProperty("shipmentShipCost")
    private double shipmentShipCost;

    @JsonProperty("productSupplier")
    private ProductSupplierResponseDTO productSupplierResponseDTO;

    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;

    @Override
    public Shipment toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Shipment entity) {
        BeanUtils.copyProperties(entity, this, "shipmentProducts");
    }

}
