package tdc.edu.vn.project_mobile_be.entities.relationship;


import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data

public class ShipmentProductId implements Serializable {
    private UUID product_id;
    private UUID shipment_id;
}
