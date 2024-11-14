package tdc.edu.vn.project_mobile_be.services.impl;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderChangeStatusDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.order.OrderCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CouponRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.OrderRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.OrderService;

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

    private final int CART_STATUS_WISH_LIST = 0;
    private final int CART_STATUS_USER = 1;
    private final int CART_STATUS_GUEST = 2;
    private final int COUPON_PER_HUNDRED_TYPE = 0;
    private final int COUPON_PRICE_TYPE = 1;
    private final int COUPON_SHIP_TYPE = 2;
    private final double SHIP_FEE = 30000;

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
        Set<Coupon> coupons = getCouponsFromRequest(order);
        Coupon couponShip = getCouponByType(coupons, COUPON_SHIP_TYPE).orElse(null);
        Coupon couponDiscount = getCouponByType(coupons, COUPON_PER_HUNDRED_TYPE, COUPON_PRICE_TYPE).orElse(null);

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
    public List<Order> getOrderByUserId(UUID userId) {
        if(userId == null){
            throw new ParamNullException("UserId is null");
        }
        return repository.findOrderByUserId(userId);
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
        return orderEntity;
    }

    private Order populateOrderForUser(OrderCreateRequestDTO order, Cart cart, Set<Coupon> coupons,
                                       Coupon couponShip, Coupon couponDiscount) {
        Order orderEntity = populateBaseOrderEntity(order, cart, coupons, couponShip, couponDiscount);
        orderEntity.setOrderName(cart.getUser().getUserFirstName() + " " + cart.getUser().getUserLastName());
        orderEntity.setOrderPhone(cart.getUser().getUserPhone());
        orderEntity.setOrderEmail(cart.getUser().getUserEmail());
        orderEntity.setOrderAddress(cart.getUser().getUserAddress());
        orderEntity.setOrderWard(cart.getUser().getUserWard());
        orderEntity.setOrderDistrict(cart.getUser().getUserDistrict());
        orderEntity.setOrderCity(cart.getUser().getUserCity());
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




}
