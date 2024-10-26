package tdc.edu.vn.project_mobile_be.entities.relationship;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
public class SizeProductId {
    private UUID product_id;

    private UUID product_size_id;
}
