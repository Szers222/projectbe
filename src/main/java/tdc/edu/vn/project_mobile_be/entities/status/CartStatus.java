package tdc.edu.vn.project_mobile_be.entities.status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CartStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_status_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID cartStatusId;
    @Column(name = "cart_status_type", nullable = false)
    private int cartStatusType;
    @Column(name = "cart_status_name", nullable = false)
    private String cartStatusName;

    @OneToMany(mappedBy = "cartStatus" )
    private Set<Cart> carts = new HashSet<>();
}
