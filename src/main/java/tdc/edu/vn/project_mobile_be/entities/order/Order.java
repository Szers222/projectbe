package tdc.edu.vn.project_mobile_be.entities.order;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate

public class Order {
    @Id
    @Column(name = "order_id",nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    @Column(name = "order_email", columnDefinition = "text")
    private String orderEmail;

    @Column(name = "order_address", nullable = false, columnDefinition = "text")
    private String orderAddress;

    @Column(name = "order_city", nullable = false, columnDefinition = "text")
    private String orderCity;

    @Column(name = "order_district", nullable = false, columnDefinition = "text")
    private String orderDistrict;

    @Column(name = "order_ward", nullable = false, columnDefinition = "text")
    private String orderWard;

    @Column(name = "order_phone", nullable = false, columnDefinition = "text")
    private String orderPhone;

    @Column(name = "order_name", nullable = false, columnDefinition = "text")
    private String orderName;

    @Column(name = "order_payment",nullable = false,columnDefinition = "int default 0")
    private int orderPayment;

    @Column(name = "order_fee_ship",nullable = false,columnDefinition = "double default 0")
    private double orderFeeShip = 30000;

    @Column(name = "order_product_price",nullable = false,columnDefinition = "double default 0")
    private double totalPrice;

    @Column(name = "order_status",nullable = false,columnDefinition = "int default 0")
    private int orderStatus;

    @Column(name = "order_note", nullable = false, columnDefinition = "text")
    private String orderNote;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_coupon",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> coupons = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cart cart;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "order-review")
    private Set<Review> reviews = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

}
