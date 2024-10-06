package tdc.edu.vn.project_mobile_be.entities.order;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.status.OrderStatus;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
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
    private UUID id;

    @Column(name = "created_at",nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;
    @Column(name = "updated_at",nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updated_at;
    @Column(name = "order_payment",nullable = false,columnDefinition = "int default 0")
    private int payment;

    @Column(name = "order_fee_ship",nullable = false,columnDefinition = "double default 0")
    private double fee_ship;
    @Column(name = "order_product_price",nullable = false,columnDefinition = "double default 0")
    private double product_price;

    @ManyToOne
    @JoinColumn(name = "order_status_id",nullable = false)
    private OrderStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_coupon",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> coupons = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
