package tdc.edu.vn.project_mobile_be.entities.coupon;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "coupons", schema = "relationship")
@DynamicInsert
@DynamicUpdate
public class Coupon {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "coupon_type", nullable = false)
    private int type;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
}
