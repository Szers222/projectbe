package tdc.edu.vn.project_mobile_be.entities.relationship;


import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
public class CartProductId {
    private UUID product_id;
    private UUID cart_id;
}
