package tdc.edu.vn.project_mobile_be.entities.coupon;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
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
    private double couponPrice;

    @Column(name = "coupon_fee_ship")
    private double couponFeeShip;

    @Column(name = "coupon_type")
    private int couponType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "coupon")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;


    @ManyToMany(mappedBy = "coupons")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Order> order = new HashSet<>();
}
