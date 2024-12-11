package tdc.edu.vn.project_mobile_be.entities.relationship;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SizeProductId implements Serializable {
    private UUID product_id;
    private UUID product_size_id;
}
