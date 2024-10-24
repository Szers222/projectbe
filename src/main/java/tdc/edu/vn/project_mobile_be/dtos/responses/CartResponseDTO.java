package tdc.edu.vn.project_mobile_be.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponseDTO {
    UUID cartId;
    UUID userId;
    Set<CartProductResponseDTO> cartProducts;
    CartStatusResponseDTO cartStatus;
    double totalPrice;
}


