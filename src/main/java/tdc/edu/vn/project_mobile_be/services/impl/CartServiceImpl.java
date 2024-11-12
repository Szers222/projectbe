package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartProductParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class CartServiceImpl extends AbService<Cart, UUID> implements CartService {
    private final int CART_STATUS_WISH_LIST = 0;
    private final int CART_STATUS_USER = 1;
    private final int CART_STATUS_GUEST = 2;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductRepository cartProductRepository;


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
        UUID cartId = UUID.randomUUID();
        cart.setCartId(cartId);
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public Cart createCartNoUser(CartCreateRequestDTO params) {
        Cart cart = new Cart();
        UUID userID = UUID.fromString("02000000-0000-0000-0000-000000000000");
        User user = userRepository.findById(userID).get();
        UUID cartId = UUID.randomUUID();
        cart.setCartId(cartId);
        cart.setCartStatus(CART_STATUS_GUEST);
        cart.setUser(user);
        Cart cartSaved = cartRepository.save(cart);
        Set<CartProduct> cartProducts = cartSaved.getCartProducts();
        CartProduct cartProduct = params.getCartProductParamsDTO().toEntity();
        Optional<Product> productOp = productRepository.findById(params.getCartProductParamsDTO().getProductIds());
        if (productOp.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        Product product = productOp.get();

        cartProduct.setCart(cartSaved);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(params.getCartProductParamsDTO().getProductQuantity());
        cartProductRepository.save(cartProduct);
        cartSaved.setCartProducts(cartProducts);

        return cartSaved;
    }

    @Override
    public Cart updateProductToCart(CartUpdateRequestDTO params, UUID cartId) {
        if (params.getCartProductParamsDTO().isEmpty()) {
            throw new ParamNullException("Cart product is required");
        }
        if (cartId == null) {
            throw new ParamNullException("Cart not found");
        }

        Optional<Cart> cartOp = cartRepository.findById(cartId);
        if (cartOp.isEmpty()) {
            throw new EntityNotFoundException("Cart not found");
        }
        Cart cart = cartOp.get();
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProductParamsDTO paramsDTO : params.getCartProductParamsDTO()) {
            CartProduct cartProduct = paramsDTO.toEntity();
            Optional<Product> productOp = productRepository.findById(paramsDTO.getProductIds());
            if (productOp.isEmpty()) {
                throw new EntityNotFoundException("Product not found");
            }
            Product product = productOp.get();
            cartProduct.setCart(cart);
            cartProduct.setProduct(product);
            cartProductRepository.save(cartProduct);
            cartProducts.add(cartProduct);
        }
        cart.setCartProducts(cartProducts);
        return cartRepository.save(cart);
    }
    public CartResponseDTO getAll(){
        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        List<Cart> listCart = cartRepository.findAll();
        if(listCart.isEmpty()){
            throw new EntityNotFoundException("Cart not found");
        }
        for (Cart cart : listCart){
            CartProduct cartProduct = cartProductRepository.findByCartId(cart.getCartId());
            cartProduct.setCart(cart);
            cartResponseDTO.toDto(cart);
        }
        return cartResponseDTO;
    }


}
