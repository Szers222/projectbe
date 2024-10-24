package tdc.edu.vn.project_mobile_be.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequestDTO {
    UUID userId;
    UUID productId;
    int quantity;
    // Default value for cartStatusId
    UUID cartStatusId = UUID.fromString("00000000-0000-0000-0000-000000000000");
}

