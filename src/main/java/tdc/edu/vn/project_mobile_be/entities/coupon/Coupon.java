package tdc.edu.vn.project_mobile_be.entities.coupon;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.type.CouponType;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "coupons")
@DynamicInsert
@DynamicUpdate
public class Coupon {
    @Id
    @Column(name = "coupon_id",nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "coupon_name", nullable = false)
    private String name;

    @Column(name = "coupon_code", nullable = false)
    private String code;

    @Column(name = "coupon_release", nullable = false)
    private Timestamp release;

    @Column(name = "coupon_expire")
    private Timestamp expire;

    @Column(name = "coupon_quantity", nullable = false)
    private int quantity;

    @Column(name = "coupon_per_hundred")
    private float per_hundred;

    @Column(name = "coupon_price")
    private String price;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "coupon_type_id")
    private CouponType type;

    @ManyToMany(mappedBy = "coupons")
    @ToString.Exclude
    private Set<Order> products = new HashSet<>();
}
