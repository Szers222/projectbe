package tdc.edu.vn.project_mobile_be.entities.cart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.status.CartStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts")
@DynamicInsert
@DynamicUpdate

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cart_status_id")
    private CartStatus status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartProduct> cartProducts = new HashSet<>();

}
