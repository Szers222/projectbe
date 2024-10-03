package tdc.edu.vn.project_mobile_be.entities.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@Table(name = "products")
@Entity
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @Column(name = "product_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private double price;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_views")
    private int views;

    @Column(name = "coupon_id", insertable = false, updatable = false)
    private UUID coupon_id;

    @Column(name = "post_id")
    private UUID post_id;

    @Column(name = "product_rating")
    private double rating;

    @Column(name = "product_supplier_id")
    private String supplier_id;

    @Column(name = "product_year_of_manufacture")
    private int year_of_manufacture;

    @Column(name = "product_created_at")
    private Timestamp created_at;

    @Column(name = "product_updated_at")
    private Timestamp updated_at;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
