package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface CartService extends IService<Cart, UUID> {
    Cart createCartByUser(UUID userId);
}
