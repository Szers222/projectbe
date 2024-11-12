package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl extends AbService<Cart, UUID> implements CartService {
    private final int NON_CART_STATUS = 0;
    private final int ACTIVE_CART_STATUS = 1;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart createCartByUser(UUID userId) {
        if (userId == null) {
            throw new EntityNotFoundException("User not found");
        }
        Optional<User> userOp = userRepository.findById(userId);
        if (userOp.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        User user = userOp.get();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartStatus(ACTIVE_CART_STATUS);
        return cartRepository.save(cart);
    }
}
