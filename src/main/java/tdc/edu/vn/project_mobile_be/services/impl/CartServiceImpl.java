package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdc.edu.vn.project_mobile_be.dtos.requests.CartRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponseDTO addToCart(CartRequestDTO cartRequestDTO) {
        Cart cart = cartRepository.findByUserId(cartRequestDTO.getUserId());
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(cartRequestDTO.getUserId());
        }

        Product product = productRepository.findById(cartRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(cartRequestDTO.getQuantity());
        cartProduct.setPrice(product.getProductPrice());

        cart.getCartProducts().add(cartProduct);
        cart = cartRepository.save(cart);

        return mapToResponseDTO(cart);
    }

    @Override
    public CartResponseDTO getCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        return mapToResponseDTO(cart);
    }

    @Override
    public void removeProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartProducts().removeIf(cartProduct -> cartProduct.getProduct().getProductId().equals(productId));
        cartRepository.save(cart);
    }

    private CartResponseDTO mapToResponseDTO(Cart cart) {
        CartResponseDTO responseDTO = new CartResponseDTO();
        responseDTO.setCartId(cart.getCartId());
        responseDTO.setUserId(cart.getUserId());

        Set<CartProductResponseDTO> cartProducts = new HashSet<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            CartProductResponseDTO productResponseDTO = new CartProductResponseDTO();
            productResponseDTO.setProductId(cartProduct.getProduct().getProductId());
            productResponseDTO.setProductName(cartProduct.getProduct().getProductName());
            productResponseDTO.setQuantity(cartProduct.getQuantity());
            productResponseDTO.setPrice(cartProduct.getPrice());
            cartProducts.add(productResponseDTO);
        }
        responseDTO.setCartProducts(cartProducts);

        double totalPrice = cartProducts.stream()
                .mapToDouble(cartProduct -> cartProduct.getPrice() * cartProduct.getQuantity())
                .sum();
        responseDTO.setTotalPrice(totalPrice);

        return responseDTO;
    }
}