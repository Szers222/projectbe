package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdc.edu.vn.project_mobile_be.dtos.requests.CartRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartStatusResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.status.CartStatus;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartStatusRepository;
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
    private final CartStatusRepository cartStatusRepository;

    @Override
    public CartResponseDTO addToCart(CartRequestDTO cartRequestDTO) {
        Cart cart = cartRepository.findByUserId(cartRequestDTO.getUserId());
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(cartRequestDTO.getUserId());
        }
        // Lấy CartStatus từ cartStatusId
        CartStatus cartStatus = cartStatusRepository.findById(cartRequestDTO.getCartStatusId())
                .orElseThrow(() -> new RuntimeException("CartStatus not found"));
        cart.setCartStatus(cartStatus);

        Product product = productRepository.findById(cartRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartProduct existingCartProduct = cart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getProductId().equals(product.getProductId()))
                .findFirst().orElse(null);

        if (existingCartProduct != null) {
            // Nếu đã có sản phẩm thì cộng thêm số lượng
            existingCartProduct.setQuantity(existingCartProduct.getQuantity() + cartRequestDTO.getQuantity());
        } else {
            // Nếu chưa có sản phẩm thì thêm mới
            CartProduct newCartProduct = new CartProduct();
            newCartProduct.setCart(cart);
            newCartProduct.setProduct(product);
            newCartProduct.setQuantity(cartRequestDTO.getQuantity());
            cart.getCartProducts().add(newCartProduct);
        }

        cart = cartRepository.save(cart);

        return mapToResponseDTO(cart);
    }

    @Override
    public CartProductResponseDTO getProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Tìm sản phẩm trong giỏ hàng dựa trên productId
        CartProduct cartProduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // Trả về thông tin sản phẩm
        CartProductResponseDTO productResponseDTO = new CartProductResponseDTO();
        productResponseDTO.setProductId(cartProduct.getProduct().getProductId());
        productResponseDTO.setProductName(cartProduct.getProduct().getProductName());
        productResponseDTO.setQuantity(cartProduct.getQuantity());
        productResponseDTO.setPrice(cartProduct.getProduct().getProductPrice()); // Giả sử sản phẩm có thuộc tính price

        return productResponseDTO;
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
    @Override
    public void confirmCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Update the cart status
        cart.setCartStatus(cartStatusRepository.findById(UUID.fromString("01000000-0000-0000-0000-000000000000"))
                .orElseThrow(() -> new RuntimeException("Cart status not found")));

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
            productResponseDTO.setPrice(cartProduct.getProduct().getProductPrice());
            cartProducts.add(productResponseDTO);
        }
        responseDTO.setCartProducts(cartProducts);

        // Tính tổng giá
        double totalPrice = cartProducts.stream()
                .mapToDouble(cartProduct -> cartProduct.getPrice() * cartProduct.getQuantity())
                .sum();
        responseDTO.setTotalPrice(totalPrice);

        // Thêm thông tin trạng thái
        if (cart.getCartStatus() != null) {
            responseDTO.setCartStatus(CartStatusResponseDTO.builder()
                    .cartStatusName(cart.getCartStatus().getCartStatusName()) // Thay đổi theo tên thuộc tính trong CartStatus
                    .cartStatusType(cart.getCartStatus().getCartStatusType()) // Thay đổi theo tên thuộc tính trong CartStatus
                    .build());
        } else {
            responseDTO.setCartStatus(null);
        }

        return responseDTO;
    }

}