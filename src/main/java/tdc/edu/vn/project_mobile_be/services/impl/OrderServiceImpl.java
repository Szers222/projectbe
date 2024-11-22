package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.order.OrderResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CouponRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.OrderRepository;
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
    private OrderRepository repository;
    @Autowired
    private CartProductRepository cartProductRepository;

    private final int CART_STATUS_WISH_LIST = 0;
    private final int CART_STATUS_USER = 1;
    private final int CART_STATUS_GUEST = 2;
    private final int COUPON_PER_HUNDRED_TYPE = 0;
    private final int COUPON_PRICE_TYPE = 1;
    private final int COUPON_SHIP_TYPE = 2;
    private final double SHIP_FEE = 30000;
    private final int PRODUCT_MIN_PRICE = 0;

    private final int ORDER_STATUS_CHECK = 0;
    private final int ORDER_STATUS_PROCESS = 1;
    private final int ORDER_STATUS_SHIP = 2;
    private final int ORDER_STATUS_COMPLETE = 3;
    private final int ORDER_STATUS_CANCEL = 4;


    @Override
    public Order createOrder(OrderCreateRequestDTO order) {
        validateOrderRequest(order);

        Cart cart = cartRepository.findById(order.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        Set<Coupon> coupons = new HashSet<>();
        Coupon couponShip = new Coupon();
        Coupon couponDiscount = new Coupon();
        if (order.getOrderCoupon() != null) {
            coupons = getCouponsFromRequest(order);
            couponShip = getCouponByType(coupons, COUPON_SHIP_TYPE).orElse(null);
            couponDiscount = getCouponByType(coupons, COUPON_PER_HUNDRED_TYPE, COUPON_PRICE_TYPE).orElse(null);
        }

        Order orderEntity;
        if (cart.getCartStatus() == CART_STATUS_GUEST) {
            orderEntity = populateOrderForGuest(order, cart, coupons, couponShip, couponDiscount);
        } else if (cart.getCartStatus() == CART_STATUS_USER) {
            orderEntity = populateOrderForUser(order, cart, coupons, couponShip, couponDiscount);
        } else {
            throw new EntityNotFoundException("Unsupported cart status");
        }

        return repository.save(orderEntity);
    }

    @Override
    public Order orderChangeStatus(OrderChangeStatusDTO orderChangeStatusDTO) {
        Order order = repository.findById(orderChangeStatusDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setOrderStatus(orderChangeStatusDTO.getStatus());
        return repository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getOrderByUserId(UUID userId) {
        if(userId == null){
            throw new ParamNullException("UserId is null");
        }
        List<Order> orders = repository.findOrderByUserId(userId);
        List<CartResponseDTO> cartResponseDTOS = new ArrayList<>();

        orders.forEach(order -> {
            CartResponseDTO dto = buildCartResponse(order.getCart().getCartId());
            cartResponseDTOS.add(dto);
        });
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        orders.forEach(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.toDto(order);
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
            orderResponseDTOS.add(dto);
        });
        return orderResponseDTOS;
    }

    @Override
    public List<CartResponseDTO> getCartByOrderId(UUID orderId) {
        return List.of();
    }

    private Order populateOrderForGuest(OrderCreateRequestDTO order, Cart cart, Set<Coupon> coupons,
                                        Coupon couponShip, Coupon couponDiscount) {
        Order orderEntity = populateBaseOrderEntity(order, cart, coupons, couponShip, couponDiscount);
        orderEntity.setOrderName(order.getUserName());
        orderEntity.setOrderPhone(order.getUserPhone());
        orderEntity.setOrderEmail(order.getUserEmail());
        orderEntity.setOrderAddress(order.getUserAddress());
        orderEntity.setOrderWard(order.getOrderWard());
        orderEntity.setOrderDistrict(order.getUserDistrict());
        orderEntity.setOrderCity(order.getUserCity());
        orderEntity.getUser().setUserId(null);
        return orderEntity;
    }

    private Order populateOrderForUser(OrderCreateRequestDTO order, Cart cart, Set<Coupon> coupons,
                                       Coupon couponShip, Coupon couponDiscount) {
        Order orderEntity = populateBaseOrderEntity(order, cart, coupons, couponShip, couponDiscount);
        orderEntity.setOrderName(cart.getUser().getUserFirstName() + " " + cart.getUser().getUserLastName());
        orderEntity.setOrderPhone(cart.getUser().getUserPhone());
        orderEntity.setOrderEmail(cart.getUser().getUserEmail());
        orderEntity.setOrderAddress(cart.getUser().getUserAddress());
        orderEntity.setOrderWard(cart.getUser().getDetail().getWard());
        orderEntity.setOrderDistrict(cart.getUser().getDetail().getDistrict());
        orderEntity.setOrderCity(cart.getUser().getDetail().getCity());
        orderEntity.getUser().setUserId(null);
        return orderEntity;
    }

    private Order populateBaseOrderEntity(OrderCreateRequestDTO order, Cart cart, Set<Coupon> coupons,
                                          Coupon couponShip, Coupon couponDiscount) {
        Order orderEntity = order.toEntity();
        orderEntity.setOrderPayment(order.getOrderPayment());
        orderEntity.setOrderNote(order.getOrderNote());
        orderEntity.setCoupons(coupons);
        orderEntity.setCart(cart);

        double shipFee = couponShip != null ? SHIP_FEE - couponShip.getCouponPrice() : SHIP_FEE;
        orderEntity.setOrderFeeShip(shipFee);

        double totalPrice = cart.getCartProducts().stream()
                .mapToDouble(cp -> cp.getProduct().getProductPrice() * cp.getProduct().getProductQuantity())
                .sum();

        if (couponDiscount != null) {
            if (couponDiscount.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                totalPrice -= totalPrice * couponDiscount.getCouponPerHundred();
            } else if (couponDiscount.getCouponType() == COUPON_PRICE_TYPE) {
                totalPrice -= couponDiscount.getCouponPrice();
            }
        }
        orderEntity.setTotalPrice(totalPrice);
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
            for (String couponCode : order.getOrderCoupon()) {
                Coupon coupon = couponRepository.findCouponByCode(couponCode)
                        .orElseThrow(() -> new EntityNotFoundException("Coupon not found"));
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
