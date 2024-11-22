package tdc.edu.vn.project_mobile_be.services.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.RemoveSizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

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
    @Autowired
    private ProductSizeRepository productSizeRepository;

    private final int PRODUCT_MIN_PRICE = 0;


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
        cart.setCartStatus(CART_STATUS_USER);
        cart.setGuestId(null);
        return cartRepository.save(cart);
    }

    @Override
    public Cart createCartNoUser(CartCreateRequestDTO params, HttpServletRequest request) {

        UUID guestId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("guestId".equals(cookie.getName())) {
                    guestId = UUID.fromString(cookie.getValue());
                    break;
                }
            }
        }
        if (guestId == null) {
            throw new EntityNotFoundException("Guest not found");
        }
        final UUID finalGuestId = guestId;
        Cart cartSaved = cartRepository.findByUserId(guestId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    UUID cartId = UUID.randomUUID();
                    newCart.setCartId(cartId);
                    newCart.setCartStatus(CART_STATUS_GUEST);
                    newCart.setUser(null);
                    newCart.setGuestId(finalGuestId);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(params.getSizeProduct().getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductSize productSize = productSizeRepository.findById(params.getSizeProduct().getSizeId())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProductSize(productSize);
        cartProduct.setCart(cartSaved);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(params.getSizeProduct().getProductSizeQuantity());

        cartSaved.getCartProducts().add(cartProduct);

        return cartRepository.save(cartSaved);
    }

    @Override
    public Cart addProductToCart(CartUpdateRequestDTO params, UUID cartId) {
        if (params == null) {
            throw new ParamNullException("Params not found");
        }
        if (cartId == null) {
            throw new ParamNullException("Cart not found");
        }

        Optional<Cart> cartOp = cartRepository.findById(cartId);
        if (cartOp.isEmpty()) {
            throw new EntityNotFoundException("Cart not found");
        }
        Cart cart = cartOp.get();
        Product product = productRepository.findById(params.getSizeProduct().getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductSize productSize = productSizeRepository.findById(params.getSizeProduct().getSizeId())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));
        Optional<CartProduct> cartProductOp = cartProductRepository.findByCartIdAndSizeProductId(cartId, product.getProductId(), productSize.getProductSizeId());
        CartProduct cartProduct;
        if (cartProductOp.isPresent()) {
            cartProduct = cartProductOp.get();
            cartProduct.setQuantity(cartProduct.getQuantity() + params.getSizeProduct().getProductSizeQuantity());
            cart.getCartProducts().add(cartProduct);
        } else {
            cartProduct = new CartProduct();
            cartProduct.setProductSize(productSize);
            cartProduct.setCart(cart);
            cartProduct.setProduct(product);
            cartProduct.setQuantity(params.getSizeProduct().getProductSizeQuantity());
            cart.getCartProducts().add(cartProduct);
        }
        return cartRepository.save(cart);
    }

    @Override
    public CartResponseDTO findCartByIdCart(UUID cartId) {
        if (cartId == null) {
            throw new ParamNullException("Cart ID cannot be null.");
        }
        return buildCartResponse(cartId);
    }

    @Override
    public CartResponseDTO findCartByIdUser(UUID userId) {
        if (userId == null) {
            throw new ParamNullException("User ID cannot be null.");
        }
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart for the user not found."));
        return buildCartResponse(cart.getCartId());
    }

    @Override
    public CartResponseDTO findCartByIdGuest(UUID guestId) {
        if (guestId == null) {
            throw new ParamNullException("Guest ID cannot be null.");
        }
        Cart cart = cartRepository.findByGuestId(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Cart for the guest not found."));
        return buildCartResponse(cart.getCartId());
    }

    public CartResponseDTO buildCartResponse(UUID cartId) {
    List<CartProduct> cartProducts = cartProductRepository.findByCartId(cartId);
        if (cartProducts.isEmpty()) {
            throw new ListNotFoundException("No products found in the cart.");
        }

        List<CartProductResponseDTO> dtos = new ArrayList<>();
        double total = cartProducts.stream().mapToDouble(item -> {
            CartProductResponseDTO dto = new CartProductResponseDTO();
            String imagePath = getImage(item.getProduct());
            String productName = item.getProduct().getProductName();
            String sizeName = item.getProductSize().getProductSizeName();
            int quantity = item.getQuantity();
            double productPrice = item.getProduct().getProductPrice();
            String productPriceString = formatProductPrice(productPrice);
            double totalPrice = item.getProduct().getProductPrice() * quantity;
            double productSale = item.getProduct().getProductSale();
            String productPriceSaleString = formatProductPrice(productPrice - (productPrice * productSale / 100));

            dto.setProductId(item.getProduct().getProductId());
            dto.setProductSizeId(item.getProductSize().getProductSizeId());
            dto.setProductImage(imagePath);
            dto.setProductName(productName);
            dto.setProductSize(sizeName);
            dto.setCartProductQuantity(quantity);
            dto.setCartProductPrice(productPriceString);
            dto.setCartProductTotalPrice(totalPrice);
            dto.setCartProductDiscount(productSale);
            dto.setCartProductDiscountPrice(productPriceSaleString);
            dtos.add(dto);

            return totalPrice;
        }).sum();

        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setCartProducts(dtos);
        cartResponseDTO.setCartId(cartId);
        cartResponseDTO.setCartProductTotalPrice(formatPrice(total));
        cartResponseDTO.setCartProductQuantity(cartProducts.size());

        return cartResponseDTO;
    }


    @Override
    public void deleteProductInCart(RemoveSizeProductRequestParamsDTO params, UUID cartId) {
        if (params == null) {
            throw new ParamNullException("Params not found");
        }
        if (cartId == null) {
            throw new ParamNullException("Cart item not found");
        }
        CartProduct cartProduct = cartProductRepository.
                findByCartIdAndSizeProductId(
                        cartId,
                        params.getSizeProduct().getProductId(),
                        params.getSizeProduct().getSizeId())
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        if (params.getSizeProduct().getProductSizeQuantity() != 0) {

            if (cartProduct.getQuantity() < params.getSizeProduct().getProductSizeQuantity()) {
                cartProductRepository.delete(cartProduct);
            }
            cartProduct.setQuantity(cartProduct.getQuantity() - params.getSizeProduct().getProductSizeQuantity());
        }
        if (cartProduct.getQuantity() == 0 || params.getSizeProduct().getProductSizeQuantity() == 0) {
            cartProductRepository.delete(cartProduct);
        }
    }


    private String getImage(Product product) {
        if (product.getImages().isEmpty()) {
            return null;
        }
        String imagePath = " ";
        for (ProductImage image : product.getImages()) {
            if (image.getProductImageIndex() == 1) {
                imagePath = image.getProductImagePath();
            }
        }
        return imagePath;
    }

    public String formatProductPrice(double price) {

        if (price < PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Price must be greater than 0");
        }
        return formatPrice(price);
    }

    public String formatPrice(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        format.setMinimumFractionDigits(PRODUCT_MIN_PRICE);
        format.setMaximumFractionDigits(PRODUCT_MIN_PRICE);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(price);
    }

}
