package tdc.edu.vn.project_mobile_be.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestByUserDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.order.OrderResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;
import tdc.edu.vn.project_mobile_be.interfaces.service.OrderService;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class OrderServiceImpl extends AbService<Order, UUID> implements OrderService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository repository;
    @Autowired
    private UserRepository userRepository;

    private final int CART_STATUS_WISH_LIST = 0;
    private final int CART_STATUS_USER = 1;
    private final int CART_STATUS_GUEST = 2;
    private final int CART_STATUS_PROCESS = 3;
    private final int COUPON_PER_HUNDRED_TYPE = 0;
    private final int COUPON_PRICE_TYPE = 1;
    private final int COUPON_SHIP_TYPE = 2;
    private final int PRODUCT_MIN_PRICE = 0;

    private final int ORDER_STATUS_CHECK = 0;
    private final int ORDER_STATUS_PROCESSING = 1;
    private final int ORDER_STATUS_PROCESSED = 2;
    private final int ORDER_STATUS_SHIP = 3;
    private final int ORDER_STATUS_DELIVERED = 4;
    private final int ORDER_STATUS_COMPLETE = 5;
    private final int ORDER_STATUS_CANCEL = 6;





    @Override
    @Transactional
    public Order createOrderByGuest(OrderCreateRequestDTO order) {
        validateOrderRequest(order);
        Cart cart = cartRepository.findById(order.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        Set<Coupon> coupons = new HashSet<>();
        Coupon couponShip = new Coupon();
        if (order.getOrderCoupon() != null) {
            coupons = getCouponsFromRequest(order);
            couponShip = getCouponByType(coupons, COUPON_SHIP_TYPE).orElse(null);
        }
        Order orderEntity = new Order();
        if (cart.getCartStatus() == CART_STATUS_GUEST) {
            orderEntity = populateOrderForGuest(order, cart, coupons, couponShip);
        }

        return orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public Order createOrderByUser(OrderCreateRequestByUserDTO request) {


        if (request == null || request.getUserId() == null) {
            throw new ParamNullException("Order or CartId is null");
        }

        Order order = request.toEntity();

        Cart currentCart = cartRepository.findByUserId(request.getUserId(), CART_STATUS_USER).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        if (currentCart.getCartProducts().isEmpty()) {
            throw new EntityNotFoundException("Cart is empty");
        }
        Set<Coupon> coupons = new HashSet<>();
        if (request.getOrderCoupon() != null) {
            for (UUID couponCode : request.getOrderCoupon()) {
                Coupon coupon = couponRepository.findCouponByCouponId(couponCode);
                if (coupon == null) {
                    throw new EntityNotFoundException("Coupon not found");
                }
                coupons.add(coupon);
            }
        }

        String fullName = currentCart.getUser().getUserFirstName() + " " + currentCart.getUser().getUserLastName();


        order.setOrderNote(request.getOrderNote());
        order.setOrderStatus(ORDER_STATUS_CHECK);
        order.setOrderPayment(request.getOrderPayment());
        order.setCart(currentCart);
        order.setOrderEmail(currentCart.getUser().getUserEmail());
        order.setOrderPhone(currentCart.getUser().getUserPhone());
        order.setOrderCity(currentCart.getUser().getDetail().getCity());
        order.setOrderDistrict(currentCart.getUser().getDetail().getDistrict());
        order.setOrderWard(currentCart.getUser().getDetail().getWard());
        order.setOrderAddress(currentCart.getUser().getDetail().getAddressName());
        order.setOrderFeeShip(request.getFeeShip());
        order.setTotalPrice(request.getTotalPrice());
        order.setOrderName(fullName);
        order.setCoupons(coupons);
        Order savedOrder = orderRepository.save(order);


        return savedOrder;
    }

    @Override
    public List<OrderResponseDTO> getOrderByShipperId(UUID shipperId) {
        if (shipperId != null) {
            throw new ParamNullException("ShipperId is null");
        }
        List<Order> orders = orderRepository.findOrderByShipperId(shipperId);
        return getOrderResponseDTOS(orders);

    }

    @Override
    public List<OrderResponseDTO> getOrderByUserId(UUID userId) {
        if (userId == null) {
            throw new ParamNullException("UserId is null");
        }
        List<Order> orders = orderRepository.findOrderByUserId(userId);
        return getOrderResponseDTOS(orders);
    }

    @Override
    public List<OrderResponseDTO> getOrderByStatus(int status) {
        List<Order> orders = orderRepository.findOrderByStatus(status);
        return getOrderResponseDTOS(orders);
    }

    @Override
    public OrderResponseDTO getOrderByCart(UUID cartId) {
        Order order = orderRepository.findOrderByCartId(cartId);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        return getOrderResponseDTOS(orders).stream().findFirst().get();
    }

    @Override
    public Order orderChangeStatus(OrderChangeStatusDTO orderChangeStatusDTO) {
        Order order = orderRepository.findById(orderChangeStatusDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        if (orderChangeStatusDTO.getStatus() == ORDER_STATUS_CANCEL) {
            Cart cart = order.getCart();
            User user = cart.getUser();
            user.setCancelCount(user.getCancelCount() + 1);
            cartRepository.save(cart);
            userRepository.save(user);
            orderRepository.delete(order);
            return null;
        } else if (orderChangeStatusDTO.getStatus() == ORDER_STATUS_PROCESSING) {
            Cart current = order.getCart();
            current.setCartStatus(CART_STATUS_PROCESS);
            cartRepository.save(current);
            Cart newCart = new Cart();
            newCart.setCartId(UUID.randomUUID());
            newCart.setCartStatus(CART_STATUS_USER);
            newCart.setUser(order.getCart().getUser());
            cartRepository.save(newCart);
        }
        order.setOrderStatus(orderChangeStatusDTO.getStatus());
        return orderRepository.save(order);
    }


    private List<OrderResponseDTO> getOrderResponseDTOS(List<Order> orders) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        orders.forEach(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            List<CartResponseDTO> cartResponseDTOS = new ArrayList<>();
            Cart cart = order.getCart();
            List<CartProductResponseDTO> cartProductResponseDTOS = new ArrayList<>();

            cart.getCartProducts().forEach(cartProduct -> {
                CartProductResponseDTO cartProductResponseDTO = new CartProductResponseDTO();
                cartProductResponseDTO.setProductId(cartProduct.getProduct().getProductId());
                cartProductResponseDTO.setProductSizeId(cartProduct.getProductSize().getProductSizeId());
                cartProductResponseDTO.setProductImage(cartProduct.getProduct().getImages().stream().findFirst().get().getProductImagePath());
                cartProductResponseDTO.setCartProductPrice(formatProductPrice(cartProduct.getProduct().getProductPrice()));
                cartProductResponseDTO.setCartProductQuantity(cartProduct.getQuantity());
                cartProductResponseDTO.setCartProductDiscount(cartProduct.getProduct().getProductSale());
                cartProductResponseDTO.setCartProductTotalPrice(cartProduct.getProduct().getProductPriceSale() * cartProduct.getQuantity());
                cartProductResponseDTO.setProductSize(cartProduct.getProductSize().getProductSizeName());
                cartProductResponseDTO.setProductName(cartProduct.getProduct().getProductName());
                cartProductResponseDTO.setCartProductDiscountPrice(formatProductPrice(cartProduct.getProduct().getProductPriceSale()));
                cartProductResponseDTOS.add(cartProductResponseDTO);
            });
            CartResponseDTO cartResponseDTO = new CartResponseDTO();
            cartResponseDTO.setCartId(cart.getCartId());
            cartResponseDTO.setCartProductTotalPrice(formatPrice(order.getTotalPrice()));
            cartResponseDTO.setCartProductQuantity(cart.getCartProducts().size());
            cartResponseDTO.setCartProducts(cartProductResponseDTOS);
            cartResponseDTOS.add(cartResponseDTO);


            if (!order.getCoupons().isEmpty()) {
                order.getCoupons().forEach(coupon -> {
                    if (coupon.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                        dto.setOrderCouponPerHundred(coupon.getCouponPerHundred());
                    }
                    if (coupon.getCouponType() == COUPON_PRICE_TYPE) {
                        dto.setOrderCouponPrice(coupon.getCouponPrice());
                    }
                    if (coupon.getCouponType() == COUPON_SHIP_TYPE) {
                        dto.setOderCouponShip(coupon.getCouponFeeShip());
                        dto.setOrderShipper(formatProductPrice(order.getOrderFeeShip() - coupon.getCouponFeeShip()));
                    }

                });
            }
            if (order.getUser() != null) {
                dto.setOrderShipperName(order.getUser().getUserFirstName() + " " + order.getUser().getUserLastName());
                dto.setOrderShipperPhone(order.getUser().getUserPhone());
            }

            dto.setItems(cartResponseDTOS);
            dto.setOrderDate(order.getCreatedAt().toString());
            dto.setOrderTotal(order.getTotalPrice());
            dto.setOrderAddress(order.getOrderAddress() + ", " + order.getOrderWard() + ", " + order.getOrderDistrict() + ", " + order.getOrderCity());
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setOrderPayment(order.getOrderPayment());
            dto.setUserEmail(order.getOrderEmail());
            dto.setUserName(order.getOrderName());
            dto.setUserPhone(order.getOrderPhone());
            dto.setOrderNote(order.getOrderNote());
            orderResponseDTOS.add(dto);
        });
        return orderResponseDTOS;
    }


    private Order populateOrderForGuest(OrderCreateRequestDTO order, Cart cart, Set<Coupon> coupons,
                                        Coupon couponShip) {
        Order orderEntity = populateBaseOrderEntity(order, cart, coupons, couponShip);
        orderEntity.setOrderName(order.getUserName());
        orderEntity.setOrderPhone(order.getUserPhone());
        orderEntity.setOrderEmail(order.getUserEmail());
        orderEntity.setOrderAddress(order.getUserAddress());
        orderEntity.setOrderWard(order.getOrderWard());
        orderEntity.setOrderDistrict(order.getUserDistrict());
        orderEntity.setOrderCity(order.getUserCity());
        orderEntity.setTotalPrice(order.getTotalPrice());
        return orderEntity;
    }

    private Order populateBaseOrderEntity(OrderCreateRequestDTO order, Cart cart, Set<Coupon> coupons,
                                          Coupon couponShip) {
        Order orderEntity = order.toEntity();
        orderEntity.setOrderPayment(order.getOrderPayment());
        orderEntity.setOrderNote(order.getOrderNote());
        orderEntity.setCoupons(coupons);
        orderEntity.setCart(cart);

        double shipFee = couponShip != null ? order.getFeeShip() - couponShip.getCouponPrice() : order.getFeeShip();
        orderEntity.setOrderFeeShip(shipFee);

        orderEntity.setTotalPrice(order.getTotalPrice());
        return orderEntity;
    }

    private Optional<Coupon> getCouponByType(Set<Coupon> coupons, int... types) {
        return coupons.stream()
                .filter(coupon -> IntStream.of(types).anyMatch(type -> type == coupon.getCouponType()))
                .findFirst();
    }

    private void validateOrderRequest(OrderCreateRequestDTO order) {
        if (order == null || order.getCartId() == null) {
            throw new ParamNullException("Order or CartId is null");
        }
    }

    private Set<Coupon> getCouponsFromRequest(OrderCreateRequestDTO order) {
        Set<Coupon> coupons = new HashSet<>();
        if (order.getOrderCoupon() != null) {
            for (UUID couponCode : order.getOrderCoupon()) {
                Coupon coupon = couponRepository.findCouponByCouponId(couponCode);
                if (coupon == null) {
                    throw new EntityNotFoundException("Coupon not found");
                }
                coupons.add(coupon);
            }
        }
        return coupons;
    }

    public CartResponseDTO buildCartResponse(UUID cartId) {
        List<CartProduct> cartProducts = cartProductRepository.findByCartId(cartId);
        if (cartProducts.isEmpty()) {
            throw new ListNotFoundException("No products found in the cart.");
        }

        List<CartProductResponseDTO> dtos = new ArrayList<>();
        double total = cartProducts.stream().mapToDouble(item -> {
            CartProductResponseDTO dto = new CartProductResponseDTO();
            String productName = item.getProduct().getProductName();
            String sizeName = item.getProductSize().getProductSizeName();
            int quantity = item.getQuantity();
            String productPrice = formatProductPrice(item.getProduct().getProductPrice());
            double totalPrice = item.getProduct().getProductPrice() * quantity;
            Set<ProductImage> productImage = item.getProduct().getImages();
            productImage.forEach(image -> {
                if (image.getProductImageIndex() == 1) {
                    dto.setProductImage(image.getProductImagePath());
                }
            });
            dto.setProductName(productName);
            dto.setProductSize(sizeName);
            dto.setCartProductQuantity(quantity);
            dto.setCartProductPrice(productPrice);
            dto.setCartProductTotalPrice(totalPrice);
            dtos.add(dto);
            return totalPrice;
        }).sum();

        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setCartProducts(dtos);
        cartResponseDTO.setCartProductTotalPrice(formatPrice(total));

        return cartResponseDTO;
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
