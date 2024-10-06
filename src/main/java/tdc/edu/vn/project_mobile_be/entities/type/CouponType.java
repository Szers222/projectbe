package tdc.edu.vn.project_mobile_be.entities.type;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "coupon_type")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CouponType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_type_id",nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "coupon_type",nullable = false)
    private int type;
    @Column(name = "coupon_type_name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<Coupon> coupons = new HashSet<>();
}
