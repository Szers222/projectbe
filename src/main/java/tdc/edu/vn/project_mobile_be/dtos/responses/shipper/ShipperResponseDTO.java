package tdc.edu.vn.project_mobile_be.dtos.responses.shipper;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tdc.edu.vn.project_mobile_be.entities.order.Order;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipperResponseDTO {
    Set<Order> order;
}
