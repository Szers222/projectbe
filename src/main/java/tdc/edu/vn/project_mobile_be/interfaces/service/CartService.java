package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestBuyNowDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.RemoveSizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface CartService extends IService<Cart, UUID> {
    Cart createCartByUser(UUID userId);

    Cart createCartNoUser(CartCreateRequestDTO cartCreateRequestDTOd);

    Cart addProductToCart(CartUpdateRequestDTO cartUpdateRequestDTO, UUID cartId);

    CartResponseDTO findCartByIdCart(UUID cartId);

    CartResponseDTO findCartByIdUser(UUID userId);

    CartResponseDTO findCartByIdUserBuyNow(UUID userId);

    CartResponseDTO findCartWishlistByIdUser(UUID userId);

    CartResponseDTO findCartByIdGuest(UUID guestId);

    void deleteProductInCart(RemoveSizeProductRequestParamsDTO params, UUID cartId);

    Cart createBuyNowCartByUser(CartCreateRequestBuyNowDTO params);
}
