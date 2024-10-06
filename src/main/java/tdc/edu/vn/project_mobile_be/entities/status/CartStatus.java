package tdc.edu.vn.project_mobile_be.entities.status;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "cart_status")
@DynamicInsert
@DynamicUpdate
public class CartStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_status_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "cart_status_type", nullable = false)
    private int type;
    @Column(name = "cart_status_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "status" )
    private Set<Cart> carts = new HashSet<>();
}
