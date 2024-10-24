package tdc.edu.vn.project_mobile_be.entities.relationship;

import jakarta.persistence.*;
import lombok.*;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

import java.util.UUID;

@Data
@Table(name = "cart_products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_product_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID cartProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false, columnDefinition = "int default 1")
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;
}
