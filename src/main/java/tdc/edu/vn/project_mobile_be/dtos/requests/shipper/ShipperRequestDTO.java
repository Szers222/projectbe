package tdc.edu.vn.project_mobile_be.dtos.requests.shipper;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipperRequestDTO {
    String oder;
}
