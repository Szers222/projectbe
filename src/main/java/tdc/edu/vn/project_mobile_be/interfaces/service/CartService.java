package tdc.edu.vn.project_mobile_be.interfaces.service;


import tdc.edu.vn.project_mobile_be.dtos.requests.CartRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartResponseDTO;

import java.util.UUID;

public interface CartService {
    CartResponseDTO addToCart(CartRequestDTO cartRequestDTO);
    CartResponseDTO getCartByUserId(UUID userId);
    void removeProductFromCart(UUID cartId, UUID productId);
}



