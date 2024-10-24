package tdc.edu.vn.project_mobile_be.dtos.responses;

import lombok.Data;
import java.util.UUID;
import java.util.Set;

@Data
public class CartResponseDTO {
    private UUID cartId;
    private UUID userId;
    private Set<CartProductResponseDTO> cartProducts;
    private double totalPrice;
}


