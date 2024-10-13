package tdc.edu.vn.project_mobile_be.entities.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
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
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Coupon {
    @Id
    @Column(name = "coupon_id",nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID couponId;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    @Column(name = "coupon_release", nullable = false)
    private Timestamp couponRelease;

    @Column(name = "coupon_expire")
    private Timestamp couponExpire;

    @Column(name = "coupon_quantity", nullable = false)
    private int couponQuantity;

    @Column(name = "coupon_per_hundred")
    private float couponPerHundred;

    @Column(name = "coupon_price")
    private String couponPrice;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "coupon_type_id")
    private CouponType couponType;

    @ManyToMany(mappedBy = "coupons")
    @ToString.Exclude
    private Set<Order> products = new HashSet<>();
}
